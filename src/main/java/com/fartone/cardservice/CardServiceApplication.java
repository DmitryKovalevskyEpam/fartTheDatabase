/**
 * Provides the main application classes and configurations.
 *
 * <p>This package contains the core Spring Boot application class,
 * configuration classes, and main application components.</p>
 *
 * @author Anna Kovalevskaja
 * @version 1.0
 * @since 2025
 * main
 */

package com.fartone.cardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CardServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CardServiceApplication.class, args);
    }
}
