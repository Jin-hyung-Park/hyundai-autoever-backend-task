package com.autoever.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 애플리케이션의 진입점입니다.
 */
@SpringBootApplication
public class BackendApplication {

    /**
     * 애플리케이션을 실행합니다.
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        System.out.println("Backend Application is running...");
    }

}
