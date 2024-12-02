package com.traplaner.mypageservice.mypage.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController // 비동기 방식
@RequestMapping("/display")
@Slf4j
public class ImageController {

    @Value("${file.upload.root-path}")
    private String rootImgPath;

    @Value("${file.upload.root-path-profile}")
    private String rootProfileImgPath;

    @Value("${file.upload.root-path-travel}")
    private String rootTravelImgPath;

    @GetMapping("/{fileName}")
    public ResponseEntity<?> getImage(@PathVariable String fileName) {

        log.info("-------------------------> ImageController:getImage");
//        String fullPath = String.format("%s/%s", rootProfileImgPath, fileName);
        String fullPath = String.format("%s/%s", rootImgPath, fileName);
        log.info("filename: {}", fileName);
        log.info("fullPath: {}", fullPath);

        File file = new File(fullPath);
        ResponseEntity<byte[]> result = null;

        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            return
                    new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

//        return result;

    }
}
