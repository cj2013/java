package com.chmc.editserverfolder;

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
public class EditServerFolderApplication {

    public static void main(String[] args) {
        SpringApplication.run(EditServerFolderApplication.class, args);
    }

    @Value("${server.port}")
    String serverPort;

    @RequestMapping(value = "folder/getname")
    public String getName(@RequestParam String name){
        return "Hi, folder name is " + name + ", and port is " + serverPort;
    }
}
