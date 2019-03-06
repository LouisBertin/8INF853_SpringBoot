package com.example.demo;

import com.example.demo.controller.EmployeeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.File;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        new File(EmployeeController.uploadDirectory).mkdir();
        SpringApplication.run(DemoApplication.class, args);
    }

}

