package com.sj.springboot.rest_api.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pending_orders")
public class PendingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticker;
    private String type; // BUY or SELL
    private Double targetPrice;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PENDING, EXECUTED

    private LocalDateTime createdAt;
    private LocalDateTime executedAt;

    // Optional: for SELL orders after execution
    private Double executedPrice;
    private Double profit;
    private Double profitPercentage;

    // getters and setters
}
