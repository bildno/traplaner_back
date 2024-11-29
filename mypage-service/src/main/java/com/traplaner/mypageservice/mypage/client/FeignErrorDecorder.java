package com.traplaner.mypageservice.mypage.client;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecorder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        // methodKey: feign client에서 호출한 메서드명.
        switch (response.status()) {
            case 400:
                break;
            case 403:
                if (methodKey.contains("getUsersByIds")) {
                    return new ResponseStatusException(
                            HttpStatus.FORBIDDEN, "security permitAll error");
                }
                break;
            case 404:
                break;
        }


        return null;
    }
}


