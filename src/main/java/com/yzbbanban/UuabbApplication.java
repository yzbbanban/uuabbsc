package com.yzbbanban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UuabbApplication {

    public static void main(String[] args) {
        SpringApplication.run(UuabbApplication.class, args);
    }
}
