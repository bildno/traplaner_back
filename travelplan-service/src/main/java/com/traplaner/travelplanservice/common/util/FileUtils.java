package com.traplaner.travelplanservice.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class FileUtils {

    public static String uploadFile(MultipartFile profileImage, String rootPath) {

        if (!StringUtils.hasText(profileImage.getOriginalFilename())) return null;

        log.info("rootPath: {}", rootPath);
        File root = new File(rootPath);
        if(!root.exists()) root.mkdirs();

        String newFileName = UUID.randomUUID()
                .toString()
                .replace("-", "");

        log.info("원본이미지: {}",  profileImage.getOriginalFilename() );

        String fileExt = profileImage.getOriginalFilename().substring(
                profileImage.getOriginalFilename().lastIndexOf(".")
        );

        newFileName += fileExt;

        File uploadPath = new File(rootPath, newFileName);
        log.info("업로드파일: {} ", uploadPath);

        try {
            profileImage.transferTo(uploadPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("완성된 경로: {}", uploadPath.getPath());

        // 프로파일 이미지 경로: C:/devlop/profile/upload/kajfljslfjdslf-fdkfsjlf.jpg
        // 트래블 이미지 경로: c:/devlop/travel/upload/kajfljslfjdslf-fdkfsjlf.jpg
        // 리턴 경로: /kajfljslfjdslf-fdkfsjlf.jpg
        return uploadPath.getPath().substring(rootPath.length() + 1);
    }
}
