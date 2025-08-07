package com.sj.springboot.rest_api.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trading")
public class Trading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_ticker", length = 10, nullable = false)
    private String stockTicker;

    @Column(name = "volume_to_trade", nullable = false)
    private Integer volumeToTrade;

    /**
     * true = BUY, false = SELL
     */
    @Column(name = "buy_sell", nullable = false)
    private Boolean buy;

    @Column(name = "price_of_action", precision = 10, scale = 2, nullable = false)
    private BigDecimal priceOfAction;

    @Column(nullable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp = LocalDateTime.now();

    public Trading() {}

    // Getters & setters
    public Long getId() { return id; }
    public String getStockTicker() { return stockTicker; }
    public void setStockTicker(String stockTicker) { this.stockTicker = stockTicker; }
    public Integer getVolumeToTrade() { return volumeToTrade; }
    public void setVolumeToTrade(Integer volumeToTrade) { this.volumeToTrade = volumeToTrade; }
    public Boolean getBuy() { return buy; }
    public void setBuy(Boolean buy) { this.buy = buy; }
    public BigDecimal getPriceOfAction() { return priceOfAction; }
    public void setPriceOfAction(BigDecimal priceOfAction) { this.priceOfAction = priceOfAction; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
