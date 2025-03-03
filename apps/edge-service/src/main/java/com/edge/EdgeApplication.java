package com.edge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
 * Project: liftinator
 * File: EdgeApplication.java
 * Author: Chris Harper
 * The EdgeApplication is a basic entry point for a Spring Boot application
 */
@Slf4j
@SpringBootApplication
@EnableScheduling
public class EdgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdgeApplication.class, args);
    }

}
