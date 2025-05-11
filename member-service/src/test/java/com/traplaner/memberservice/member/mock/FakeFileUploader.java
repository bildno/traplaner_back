package com.traplaner.memberservice.member.mock;

import com.traplaner.memberservice.member.service.port.FileUploader;
import org.springframework.web.multipart.MultipartFile;

public class FakeFileUploader implements FileUploader {

    @Override
    public String uploadtos3bucket(MultipartFile profileImage) {
        return "프로필사진이야";
    }
}
