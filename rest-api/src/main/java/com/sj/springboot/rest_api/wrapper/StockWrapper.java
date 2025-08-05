// src/main/java/com/sj/springboot/rest_api/wrapper/StockWrapper.java
package com.sj.springboot.rest_api.wrapper;

import yahoofinance.Stock;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockWrapper {

    private final Stock stock;
    private final LocalDateTime lastAccess;

    public StockWrapper(Stock stock) {
        this.stock = stock;
        this.lastAccess = LocalDateTime.now();
    }

    public LocalDateTime getLastAccess() {
        return lastAccess;
    }

    // changed from Stock --> String
    public String getStock() {
        return stock.getName();
    }

    public String getName() {
        return stock.getName();
    }

    public BigDecimal getPrice() {
        if (stock.getQuote() != null) {
            return stock.getQuote().getPrice();
        }
        return null;
    }

    public String getCurrency() {
        return stock.getCurrency();
    }

    public String getSymbol() {
        return stock.getSymbol();
    }
}
