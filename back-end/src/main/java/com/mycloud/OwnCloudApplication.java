package com.mycloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class OwnCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwnCloudApplication.class, args);
    }

}

