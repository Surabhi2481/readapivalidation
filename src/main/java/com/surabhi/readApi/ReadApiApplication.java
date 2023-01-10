package com.surabhi.readApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages={"com.surabhi.readApi"})
public class ReadApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReadApiApplication.class, args);
    }
}
