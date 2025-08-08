package com.sj.springboot.rest_api.service;

import com.sj.springboot.rest_api.controller.StockFetcherService;
import com.sj.springboot.rest_api.dto.TransactRequest;
import com.sj.springboot.rest_api.dto.TransactResponse;
import com.sj.springboot.rest_api.entity.PendingOrder;
import com.sj.springboot.rest_api.entity.Portfolio;
import com.sj.springboot.rest_api.entity.Trading;
import com.sj.springboot.rest_api.entity.Orders;
import com.sj.springboot.rest_api.repository.PendingOrderRepository;
import com.sj.springboot.rest_api.repository.PortfolioRepository;
import com.sj.springboot.rest_api.repository.TradingRepository;
import com.sj.springboot.rest_api.repository.OrdersRepository;
import com.sj.springboot.rest_api.service.TransactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
@Service
public class TransactServiceImpl implements TransactService {

    @Autowired
    private PendingOrderRepository pendingOrderRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    @Transactional
    public TransactResponse execute(TransactRequest request) {
        PendingOrder order = new PendingOrder();
        order.setTicker(request.getTicker());
        order.setType(request.getType());
        order.setTargetPrice(request.getPrice());
        order.setQuantity(request.getQuantity());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        // SELL: check if we have enough shares
        if ("SELL".equalsIgnoreCase(request.getType())) {
            Portfolio portfolio = portfolioRepository.findByTicker(request.getTicker())
                    .orElseThrow(() -> new RuntimeException("No holdings for ticker: " + request.getTicker()));
            if (portfolio.getQuantity() < request.getQuantity()) {
                throw new RuntimeException("Not enough shares to sell");
            }
        }

        pendingOrderRepository.save(order);

        return new TransactResponse(1, "Order placed successfully", order.getId(), null, null, null);
    }
}