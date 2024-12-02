package com.traplaner.travelboardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TravelboardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelboardServiceApplication.class, args);
    }

}
