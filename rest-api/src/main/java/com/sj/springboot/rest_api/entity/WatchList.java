// src/main/java/com/portfolio/entity/WatchList.java
package com.sj.springboot.rest_api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "watch_list")
public class WatchList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "stock_ticker", nullable = false, unique = true)
    private String stockTicker;
    
    @Column(name = "added_date", nullable = false)
    private LocalDateTime addedDate;

    @Column(name = "current_price", nullable = false)
    private Double current_price;
    
    // Constructors
    public WatchList() {}
    
    public WatchList(String stockTicker, LocalDateTime addedDate) {
        this.stockTicker = stockTicker;
        this.addedDate = addedDate;
        // this.current_price = current_price;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getStockTicker() { return stockTicker; }
    public void setStockTicker(String stockTicker) { this.stockTicker = stockTicker; }
    
    public LocalDateTime getAddedDate() { return addedDate; }
    public void setAddedDate(LocalDateTime addedDate) { this.addedDate = addedDate; }

    // public void setStockName(Object name) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'setStockName'");
    // }

    // public void setCurrentPrice(Object price) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'setCurrentPrice'");
    // }

    // public void setCurrency(Object currency) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'setCurrency'");
    // }
}