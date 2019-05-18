package com.merpam.onenight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OneNightApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneNightApplication.class, args);
    }

}
