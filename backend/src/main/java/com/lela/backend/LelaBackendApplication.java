package com.lela.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point of the Spring Boot backend application.
 *
 * For MVP we keep the bootstrap class minimal: all infrastructure wiring is done
 * automatically by Spring Boot through component scanning and auto-configuration.
 */
@SpringBootApplication
public class LelaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LelaBackendApplication.class, args);
    }
}
