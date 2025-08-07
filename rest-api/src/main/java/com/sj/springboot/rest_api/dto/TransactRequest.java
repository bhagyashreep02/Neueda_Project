// TransactRequest.java
package com.sj.springboot.rest_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransactRequest {
    @NotBlank
    private String ticker;

    @NotNull @Min(1)
    private Integer quantity;

    @NotNull @Min(0)
    private Double price;

    @NotBlank
    private String action;  // "BUY" or "SELL"

    // getters & setters

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
