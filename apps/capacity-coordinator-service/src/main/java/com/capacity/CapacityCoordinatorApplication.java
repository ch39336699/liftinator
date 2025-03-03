package com.capacity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;


/*
 * Project: liftinator
 * File: CapacityCoordinatorApplication.java
 * Author: Chris Harper
 * The CapacityCoordinatorApplication is a basic entry point for a Spring Boot application
 */
@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class CapacityCoordinatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(CapacityCoordinatorApplication.class, args);
    }
}
