package com.exalt.rpn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RpnApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpnApplication.class, args);
    }

}
