package com.chmc.useroauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserOauthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserOauthServiceApplication.class, args);
    }
}
