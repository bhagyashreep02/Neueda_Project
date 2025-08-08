package com.sj.springboot.rest_api.wrapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockWrapper {

    private final String name;
    private final String symbol;
    private final BigDecimal price;
    private final String currency;
    private final LocalDateTime lastAccess;

    public StockWrapper(String name, String symbol, BigDecimal price, String currency, LocalDateTime lastAccess) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.currency = currency;
        this.lastAccess = lastAccess;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDateTime getLastAccess() {
        return lastAccess;
    }

    public String getStock() {
        return name;
    }
}
