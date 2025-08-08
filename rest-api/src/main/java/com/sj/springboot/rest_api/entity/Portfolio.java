// src/main/java/com/sj/springboot/rest_api/entity/Portfolio.java
package com.sj.springboot.rest_api.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_ticker", nullable = false)
    private String stockTicker;

    @Column(name = "volume", nullable = false)
    private Integer volume;

    @Column(name = "price_of_buying", precision = 19, scale = 4)
    private BigDecimal priceOfBuying;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    // Constructors
    public Portfolio() {}

    public Portfolio(String stockTicker, Integer volume, BigDecimal priceOfBuying, LocalDateTime timestamp) {
        this.stockTicker = stockTicker;
        this.volume = volume;
        this.priceOfBuying = priceOfBuying;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStockTicker() { return stockTicker; }
    public void setStockTicker(String stockTicker) { this.stockTicker = stockTicker; }

    public Integer getVolume() { return volume; }
    public void setVolume(Integer volume) { this.volume = volume; }

    public BigDecimal getPriceOfBuying() { return priceOfBuying; }
    public void setPriceOfBuying(BigDecimal priceOfBuying) { this.priceOfBuying = priceOfBuying; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    // Business Logic
    public BigDecimal getTotalInvestment() {
        return priceOfBuying.multiply(BigDecimal.valueOf(volume));
    }

    public BigDecimal getCurrentValue(BigDecimal currentPrice) {
        return currentPrice.multiply(BigDecimal.valueOf(volume));
    }

    public BigDecimal getGainLoss(BigDecimal currentPrice) {
        return getCurrentValue(currentPrice).subtract(getTotalInvestment());
    }

    public BigDecimal getGainLossPercentage(BigDecimal currentPrice) {
        BigDecimal totalInvestment = getTotalInvestment();
        if (totalInvestment.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return getGainLoss(currentPrice)
                .divide(totalInvestment, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    public boolean isProfitable(BigDecimal currentPrice) {
        return currentPrice.compareTo(priceOfBuying) > 0;
    }

    public BigDecimal getAverageCostPerShare() {
        return priceOfBuying;
    }

    public Integer getShares() {
        return volume;
    }

    public void addShares(Integer additionalShares, BigDecimal purchasePrice) {
        if (additionalShares <= 0) {
            throw new IllegalArgumentException("Additional shares must be positive");
        }

        BigDecimal currentTotalCost = getTotalInvestment();
        BigDecimal additionalCost = purchasePrice.multiply(BigDecimal.valueOf(additionalShares));
        BigDecimal newTotalCost = currentTotalCost.add(additionalCost);

        this.volume += additionalShares;
        this.priceOfBuying = newTotalCost
                .divide(BigDecimal.valueOf(this.volume), 2, BigDecimal.ROUND_HALF_UP);
        this.timestamp = LocalDateTime.now();
    }

    public void reduceShares(Integer sharesToSell) {
        if (sharesToSell <= 0) {
            throw new IllegalArgumentException("Shares to sell must be positive");
        }
        if (sharesToSell > this.volume) {
            throw new IllegalArgumentException("Cannot sell more shares than owned");
        }

        this.volume -= sharesToSell;
        this.timestamp = LocalDateTime.now();
    }

    public boolean hasEnoughShares(Integer requestedShares) {
        return this.volume >= requestedShares;
    }

    public BigDecimal getProfitIfSold(BigDecimal sellingPrice, Integer sharesToSell) {
        if (sharesToSell > this.volume) {
            throw new IllegalArgumentException("Cannot sell more shares than owned");
        }

        BigDecimal sellingValue = sellingPrice.multiply(BigDecimal.valueOf(sharesToSell));
        BigDecimal costBasis = this.priceOfBuying.multiply(BigDecimal.valueOf(sharesToSell));
        return sellingValue.subtract(costBasis);
    }

    // Utility methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Portfolio)) return false;
        Portfolio that = (Portfolio) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(stockTicker, that.stockTicker) &&
                Objects.equals(volume, that.volume) &&
                Objects.equals(priceOfBuying, that.priceOfBuying) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stockTicker, volume, priceOfBuying, timestamp);
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "id=" + id +
                ", stockTicker='" + stockTicker + '\'' +
                ", volume=" + volume +
                ", priceOfBuying=" + priceOfBuying +
                ", timestamp=" + timestamp +
                '}';
    }

    // Validation
    public boolean isValid() {
        return stockTicker != null && !stockTicker.trim().isEmpty() &&
                volume != null && volume > 0 &&
                priceOfBuying != null && priceOfBuying.compareTo(BigDecimal.ZERO) > 0 &&
                timestamp != null;
    }

    public void validate() {
        if (!isValid()) {
            throw new IllegalStateException("Portfolio entity is in invalid state");
        }
    }
}
