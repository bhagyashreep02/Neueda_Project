// src/main/java/com/portfolio/entity/Orders.java
package com.sj.springboot.rest_api.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "stock_ticker", nullable = false)
    private String stockTicker;
    
    @Column(name = "volume", nullable = false)
    private Integer volume;
    
    @Column(name = "price_of_buying", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceOfBuying;
    
    @Column(name = "buy_sell", nullable = false)
    private Integer buySell; // 0 for sell, 1 for buy
    
    @Column(name = "status_code", nullable = false)
    private String statusCode;
    
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
    
    public Orders() {}
    
    public Orders(String stockTicker, Integer volume, BigDecimal priceOfBuying, 
                  Integer buySell, String statusCode, LocalDateTime timestamp) {
        this.stockTicker = stockTicker;
        this.volume = volume;
        this.priceOfBuying = priceOfBuying;
        this.buySell = buySell;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }
 

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getStockTicker() { return stockTicker; }
    public void setStockTicker(String stockTicker) { this.stockTicker = stockTicker; }
    
    public Integer getVolume() { return volume; }
    public void setVolume(Integer volume) { this.volume = volume; }
    
    public BigDecimal getPriceOfBuying() { return priceOfBuying; }
    public void setPriceOfBuying(BigDecimal priceOfBuying) { this.priceOfBuying = priceOfBuying; }
    
    public Integer getBuySell() { return buySell; }
    public void setBuySell(Integer buySell) { this.buySell = buySell; }
    
    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}