package com.sj.springboot.rest_api.service;

import com.sj.springboot.rest_api.dto.PortfolioSummary;
import com.sj.springboot.rest_api.entity.Portfolio;
import com.sj.springboot.rest_api.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    BigDecimal totalInvestment = BigDecimal.ZERO;


    public PortfolioSummary getPortfolioSummary() {
        List<Portfolio> portfolios = portfolioRepository.findAll();

        Map<String, Map<String, Object>> holdings = new HashMap<>();

        for (Portfolio portfolio : portfolios) {
            String ticker = portfolio.getStockTicker().toUpperCase();
            Map<String, Object> details = holdings.getOrDefault(ticker, new HashMap<>());

            int currentVolume = (int) details.getOrDefault("volume", 0);
            details.put("volume", currentVolume + portfolio.getVolume());
            details.put("priceOfBuying", portfolio.getPriceOfBuying());
            details.put("timestamp", portfolio.getTimestamp());

            holdings.put(ticker, details);

            totalInvestment = totalInvestment.add(
                portfolio.getPriceOfBuying().multiply(BigDecimal.valueOf(portfolio.getVolume()))
            );
        }

        BigDecimal currentMarketValue = totalInvestment.multiply(BigDecimal.valueOf(1.05)); // mock 5% gain
        BigDecimal totalGain = currentMarketValue.subtract(totalInvestment);
        BigDecimal performance = totalInvestment.compareTo(BigDecimal.ZERO) > 0
                ? totalGain.divide(totalInvestment, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100))
                : BigDecimal.ZERO;

        return new PortfolioSummary(holdings, totalGain, currentMarketValue, totalInvestment, performance);
    }
}
