
package com.sj.springboot.rest_api.dto;
import com.sj.springboot.rest_api.controller.StockFetcherService;
import java.time.LocalDateTime;

import org.nd4j.linalg.cpu.nativecpu.bindings.Nd4jCpu.absolute_difference_loss;

public class TransactResponse {
    private Long orderId;
    private Integer statusCode;      // 1=success, 2=failed
    private LocalDateTime executedAt;

    // Only populated for SELL:
    private Double profitPrice;
    private Double profitPercentage;

    // getters & setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }

    public Double getProfitPrice() {
        return profitPrice;
    }

    public void setProfitPrice(Double profitPrice) {
        this.profitPrice = profitPrice;
    }

    public Double getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(Double profitPercentage) {
        this.profitPercentage = profitPercentage;
    }
}
