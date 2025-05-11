package com.traplaner.memberservice.member.infrastructure;

import com.traplaner.memberservice.member.service.port.FileUploader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
public class FileUploadImpl implements FileUploader {
    @Override
    public String uploadtos3bucket(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        // 파일 저장 로직
        return fileName;
    }


}