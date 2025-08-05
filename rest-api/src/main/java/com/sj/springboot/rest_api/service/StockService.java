// src/main/java/com/sj/springboot/rest_api/service/StockService.java
package com.sj.springboot.rest_api.service;

import yahoofinance.Stock;
import java.time.LocalDateTime;

public class StockService {
    private final Stock stock;
    private final LocalDateTime lastAccess;

    public StockService(Stock stock) {
        this.stock = stock;
        this.lastAccess = LocalDateTime.now();
    }

    public LocalDateTime getLastAccess() {
        return lastAccess;
    }

    public Stock getStock() {
        return stock;
    }
}
