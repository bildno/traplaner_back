package com.traplaner.mypageservice.mypage.common.config;

import com.traplaner.mypageservice.mypage.client.FeignErrorDecorder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FeignErrorDecorder getFeignErrorDecorder() {
        return new FeignErrorDecorder();
    }

}
