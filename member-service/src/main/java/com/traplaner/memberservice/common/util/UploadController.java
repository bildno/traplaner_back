package com.traplaner.memberservice.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@Slf4j
public class UploadController {

    /*
    file:upload:root-path: C:/devlop/upload
     */

    @Value("${file.upload.root-path}")
    private String rootPath;

    @GetMapping("/upload-form")
    public String uploadForm() {
        return "upload/upload-form";
    }

    @Value("${file.upload.root-path-profile}")
    private String rootProfileImgPath;

    @Value("${file.upload.root-path-travel}")
    private String rootTravelImgPath;

    @Value("{file.upload.root-path-reservation")
    private String rootReservationImgPath;

    @PostMapping("/upload-file")
    public String uploadForm(@RequestParam("thumbnail") MultipartFile file) {

        log.info("file-name: {}", file.getOriginalFilename());
        log.info("file-size: {}", file.getSize());
        log.info("file-type: {}", file.getContentType());

        System.out.println("rootProfileImgPath = " + rootProfileImgPath);

        File root = new File(rootPath);
        if(!root.exists()) root.mkdirs();

        UUID uuid = UUID.randomUUID();
        log.info("uuid: {}", uuid.toString());

        String fileName = uuid.toString();
        fileName = fileName.replace("-", "");

        // 원본파일
        String fileExt = file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf(".")
        );

        log.debug("확장자: {}", fileExt);
        // 완성된 경로를 가진 File 객체 생성
        File uploadFile = new File(rootPath, fileName + fileExt);
        log.info("업로드 파일: {}", uploadFile);

        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }

        return "redirect:/upload-form";
    }
}
