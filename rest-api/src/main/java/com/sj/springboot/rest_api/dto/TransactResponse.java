
package com.sj.springboot.rest_api.dto;
import com.sj.springboot.rest_api.controller.StockFetcherService;
import java.time.LocalDateTime;

import org.nd4j.linalg.cpu.nativecpu.bindings.Nd4jCpu.absolute_difference_loss;

public class TransactResponse {

    private int statusCode;
    private String message;
    private Long orderId;
    private LocalDateTime executedAt;
    private Double profitPrice;
    private Double profitPercentage;

    public TransactResponse() {}

    public TransactResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    // getters/setters
    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public LocalDateTime getExecutedAt() { return executedAt; }
    public void setExecutedAt(LocalDateTime executedAt) { this.executedAt = executedAt; }

    public Double getProfitPrice() { return profitPrice; }
    public void setProfitPrice(Double profitPrice) { this.profitPrice = profitPrice; }

    public Double getProfitPercentage() { return profitPercentage; }
    public void setProfitPercentage(Double profitPercentage) { this.profitPercentage = profitPercentage; }
}