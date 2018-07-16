package com.chmc.editserveruser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class EditServerUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(EditServerUserApplication.class, args);
    }

    @Value("${server.port}")
    String port;

    @RequestMapping(value = "/user/getname")
    public String getFile(@RequestParam String name) {
        return "Hi, user name is " + name + ", and port is " + port;
    }
}
