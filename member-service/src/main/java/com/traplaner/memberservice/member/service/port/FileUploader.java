package com.traplaner.memberservice.member.service.port;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface FileUploader {
    String uploadtos3bucket(MultipartFile profileImage);
}
