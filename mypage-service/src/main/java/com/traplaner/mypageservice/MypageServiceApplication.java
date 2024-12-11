package com.traplaner.mypageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MypageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MypageServiceApplication.class, args);
    }

}
