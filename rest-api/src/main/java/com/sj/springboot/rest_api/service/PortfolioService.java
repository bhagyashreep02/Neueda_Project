// src/main/java/com/portfolio/service/PortfolioService.java
package com.sj.springboot.rest_api.service;

import com.sj.springboot.rest_api.dto.PortfolioSummary;
import com.sj.springboot.rest_api.entity.Portfolio;
import com.sj.springboot.rest_api.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PortfolioService {
    
    @Autowired
    private PortfolioRepository portfolioRepository;
    
    public PortfolioSummary getPortfolioSummary() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        
        Map<String, Integer> holdings = new HashMap<>();
        BigDecimal totalInvestment = BigDecimal.ZERO;
        
        for (Portfolio portfolio : portfolios) {
            String ticker = portfolio.getStockTicker();
            holdings.put(ticker, holdings.getOrDefault(ticker, 0) + portfolio.getVolume());
            totalInvestment = totalInvestment.add(
                portfolio.getPriceOfBuying().multiply(BigDecimal.valueOf(portfolio.getVolume()))
            );
        }
        
        // Mock current market prices (in real app, would fetch from market API)
        BigDecimal currentMarketValue = totalInvestment.multiply(BigDecimal.valueOf(1.05)); // 5% gain mock
        BigDecimal totalGain = currentMarketValue.subtract(totalInvestment);
        BigDecimal performance = totalInvestment.compareTo(BigDecimal.ZERO) > 0 
            ? totalGain.divide(totalInvestment, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100))
            : BigDecimal.ZERO;
        
        return new PortfolioSummary(holdings, totalGain, currentMarketValue, totalInvestment, performance);
    }
}