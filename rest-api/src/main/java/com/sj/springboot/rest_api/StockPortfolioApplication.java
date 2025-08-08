// src/main/java/com/portfolio/StockPortfolioApplication.java
package com.sj.springboot.rest_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockPortfolioApplication {
    public static void main(String[] args) {
        System.setProperty("smile.engine", "jvm"); // Force JVM engine
        SpringApplication.run(StockPortfolioApplication.class, args);
    }
}