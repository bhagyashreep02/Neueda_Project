package com.sj.springboot.rest_api.service;

import com.sj.springboot.rest_api.controller.StockFetcherService;
import com.sj.springboot.rest_api.dto.TransactRequest;
import com.sj.springboot.rest_api.dto.TransactResponse;
import com.sj.springboot.rest_api.entity.Portfolio;
import com.sj.springboot.rest_api.entity.Trading;
import com.sj.springboot.rest_api.entity.Orders;
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

    private final PortfolioRepository portfolioRepo;
    private final TradingRepository tradingRepo;
    private final OrdersRepository ordersRepo;

    @Autowired
    private StockFetcherService stockFetcherService;

    @Autowired
    public TransactServiceImpl(PortfolioRepository portfolioRepo,
                               TradingRepository tradingRepo,
                               OrdersRepository ordersRepo) {
        this.portfolioRepo = portfolioRepo;
        this.tradingRepo   = tradingRepo;
        this.ordersRepo    = ordersRepo;
    }

    @Override
    @Transactional
    public TransactResponse execute(TransactRequest req) {
        String ticker = req.getTicker().toUpperCase();
        int qty       = req.getQuantity();
        BigDecimal price  = BigDecimal.valueOf(req.getPrice());
        boolean isBuy = "BUY".equalsIgnoreCase(req.getAction());

        // Load latest or create
        List<Portfolio> history = portfolioRepo.findByStockTicker(ticker);
        Portfolio port = history.stream()
                .max(Comparator.comparing(Portfolio::getTimestamp))
                .orElseGet(() -> new Portfolio(ticker, 0, BigDecimal.ZERO, LocalDateTime.now()));

        BigDecimal profitPrice      = BigDecimal.ZERO;
        BigDecimal profitPercentage = BigDecimal.ZERO;
        String statusCode;

        if (isBuy) {
            port.addShares(qty, price);
            statusCode = "1"; // success
        } else {
            if (!port.hasEnoughShares(qty)) {
                statusCode = "2"; // failed
            } else {
                profitPrice = port.getProfitIfSold(price, qty);
                BigDecimal costBasis = port.getPriceOfBuying().multiply(BigDecimal.valueOf(qty));
                profitPercentage = costBasis.compareTo(BigDecimal.ZERO) == 0 ?
                        BigDecimal.ZERO :
                        profitPrice.divide(costBasis, 4, java.math.RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                port.reduceShares(qty);
                statusCode = "1"; // success
            }
        }

        // persist portfolio snapshot
        port.setTimestamp(LocalDateTime.now());
        portfolioRepo.save(port);

        // Trading log
        Trading trade = new Trading();
        trade.setStockTicker(ticker);
        trade.setVolumeToTrade(qty);
        trade.setBuy(isBuy);
        trade.setPriceOfAction(price);
        tradingRepo.save(trade);

        // Persist order
        Orders order = new Orders(
                ticker,
                qty,
                price,
                isBuy ? 1 : 0,
                statusCode,
                LocalDateTime.now()
        );
        Orders savedOrder = ordersRepo.save(order);

        // Build response
        TransactResponse resp = new TransactResponse();
        resp.setOrderId(savedOrder.getId());
        resp.setStatusCode(Integer.parseInt(statusCode));
        resp.setExecutedAt(savedOrder.getTimestamp());

        if (!isBuy && "1".equals(statusCode)) {
            resp.setProfitPrice(profitPrice.doubleValue());
            resp.setProfitPercentage(profitPercentage.doubleValue());
        }

        return resp;
    }
}