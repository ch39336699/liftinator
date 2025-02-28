package com.capacity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class CapacityCoordinatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapacityCoordinatorApplication.class, args);
    }

}
