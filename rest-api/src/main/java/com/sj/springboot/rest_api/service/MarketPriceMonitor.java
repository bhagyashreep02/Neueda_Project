package com.sj.springboot.rest_api.service;

import com.sj.springboot.rest_api.entity.OrderStatus;
import com.sj.springboot.rest_api.entity.Orders;
import com.sj.springboot.rest_api.entity.PendingOrder;
import com.sj.springboot.rest_api.entity.Portfolio;
import com.sj.springboot.rest_api.repository.OrdersRepository;
import com.sj.springboot.rest_api.repository.PendingOrderRepository;
import com.sj.springboot.rest_api.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
public class MarketPriceMonitor {

    @Autowired
    private PendingOrderRepository pendingOrderRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private AlphavantageService stockPriceService; // you should already have this

    @Scheduled(fixedRate = 10000) // every 10 seconds
    public void checkOrders() {
        List<PendingOrder> pendingOrders = pendingOrderRepository.findByStatus(OrderStatus.PENDING);

        for (PendingOrder order : pendingOrders) {
            double marketPrice = stockPriceService.getLivePrice(order.getTicker());

            if ("BUY".equalsIgnoreCase(order.getType()) && marketPrice <= order.getTargetPrice()) {
                executeBuy(order, marketPrice);
            } else if ("SELL".equalsIgnoreCase(order.getType()) && marketPrice >= order.getTargetPrice()) {
                executeSell(order, marketPrice);
            }
        }
    }

    private void executeBuy(PendingOrder order, double executedPrice) {
        Portfolio portfolio = portfolioRepository.findByTicker(order.getTicker())
                .orElse(new Portfolio());

        portfolio.setTicker(order.getTicker());
        portfolio.setBuyPrice(executedPrice);
        portfolio.setQuantity(portfolio.getQuantity() == null ? order.getQuantity() :
                portfolio.getQuantity() + order.getQuantity());
        portfolio.setBuyDate(LocalDateTime.now());

        portfolioRepository.save(portfolio);

        order.setStatus(OrderStatus.EXECUTED);
        order.setExecutedAt(LocalDateTime.now());
        order.setExecutedPrice(executedPrice);
        pendingOrderRepository.save(order);
    }

    private void executeSell(PendingOrder order, double executedPrice) {
        Portfolio portfolio = portfolioRepository.findByTicker(order.getTicker())
                .orElseThrow(() -> new RuntimeException("No holdings found for " + order.getTicker()));

        double profit = (executedPrice - portfolio.getBuyPrice()) * order.getQuantity();
        double profitPercentage = ((executedPrice - portfolio.getBuyPrice()) / portfolio.getBuyPrice()) * 100;

        // Reduce or remove holdings
        int remainingQty = portfolio.getQuantity() - order.getQuantity();
        if (remainingQty <= 0) {
            portfolioRepository.delete(portfolio);
        } else {
            portfolio.setQuantity(remainingQty);
            portfolioRepository.save(portfolio);
        }

        order.setStatus(OrderStatus.EXECUTED);
        order.setExecutedAt(LocalDateTime.now());
        order.setExecutedPrice(executedPrice);
        order.setProfit(profit);
        order.setProfitPercentage(profitPercentage);
        pendingOrderRepository.save(order);
    }
}