package com.sj.springboot.rest_api.dto;

import java.math.BigDecimal;

public class TransactResponse {

    private int statusCode;
    private String message;
    private Long transactionId;
    private String ticker;
    private Integer quantity;
    private BigDecimal price;

    public TransactResponse() {
    }

    public TransactResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public TransactResponse(int statusCode, String message, Long transactionId,
                            String ticker, Integer quantity, BigDecimal price) {
        this.statusCode = statusCode;
        this.message = message;
        this.transactionId = transactionId;
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TransactResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", transactionId=" + transactionId +
                ", ticker='" + ticker + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
