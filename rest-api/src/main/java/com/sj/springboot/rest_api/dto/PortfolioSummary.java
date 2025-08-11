package com.sj.springboot.rest_api.dto;

import java.math.BigDecimal;
import java.util.Map;

public class PortfolioSummary {
    private Map<String, Map<String, Object>> currentHoldings; // ticker -> {volume, priceOfBuying, timestamp, ...}
    private BigDecimal totalGain;
    private BigDecimal currentMarketValue;
    private BigDecimal portfolioValue;
    private BigDecimal portfolioPerformance;

    public PortfolioSummary() {}

    public PortfolioSummary(
            Map<String, Map<String, Object>> currentHoldings,
            BigDecimal totalGain,
            BigDecimal currentMarketValue,
            BigDecimal portfolioValue,
            BigDecimal portfolioPerformance
    ) {
        this.currentHoldings = currentHoldings;
        this.totalGain = totalGain;
        this.currentMarketValue = currentMarketValue;
        this.portfolioValue = portfolioValue;
        this.portfolioPerformance = portfolioPerformance;
    }

    public Map<String, Map<String, Object>> getCurrentHoldings() {
        return currentHoldings;
    }

    public void setCurrentHoldings(Map<String, Map<String, Object>> currentHoldings) {
        this.currentHoldings = currentHoldings;
    }

    public BigDecimal getTotalGain() {
        return totalGain;
    }

    public void setTotalGain(BigDecimal totalGain) {
        this.totalGain = totalGain;
    }

    public BigDecimal getCurrentMarketValue() {
        return currentMarketValue;
    }

    public void setCurrentMarketValue(BigDecimal currentMarketValue) {
        this.currentMarketValue = currentMarketValue;
    }

    public BigDecimal getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(BigDecimal portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public BigDecimal getPortfolioPerformance() {
        return portfolioPerformance;
    }

    public void setPortfolioPerformance(BigDecimal portfolioPerformance) {
        this.portfolioPerformance = portfolioPerformance;
    }
}
