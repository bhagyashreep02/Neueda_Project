package com.sj.springboot.rest_api.dto;

import java.math.BigDecimal;

public class TransactRequest {

    private String ticker;
    private int quantity;
    private BigDecimal price;
    private String type; // e.g., "BUY" or "SELL"

    public TransactRequest() {
    }

    public TransactRequest(String ticker, int quantity, BigDecimal price, String type) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TransactRequest{" +
                "ticker='" + ticker + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", type='" + type + '\'' +
                '}';
    }
}
