package com.sj.springboot.rest_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MarketPriceMonitor {

    private static final Logger log = LoggerFactory.getLogger(MarketPriceMonitor.class);

    private final AlphavantageService alphavantageService;

    // Configure tickers to monitor
    private final List<String> tickers = Arrays.asList("AAPL", "MSFT", "GOOGL");

    public MarketPriceMonitor(AlphavantageService alphavantageService) {
        this.alphavantageService = alphavantageService;
    }

    /**
     * Scheduled to run every hour (adjust as needed)
     */
    @Scheduled(fixedRate = 60_000) // every 60 sec for testing
    public void checkPrices() {
        log.info("Running Market Price Monitor...");

        for (String ticker : tickers) {
            try {
                List<Double> prices = alphavantageService.getLastNDaysOpenPrices(ticker, 5);

                if (prices.isEmpty()) {
                    log.warn("No price data available for {}", ticker);
                    continue;
                }

                double latestPrice = prices.get(0);
                double avgPrice = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0);

                log.info("Ticker: {} | Latest Price: {} | 5-day Avg: {}", ticker, latestPrice, avgPrice);

            } catch (Exception e) {
                log.error("Error processing ticker {}: {}", ticker, e.getMessage(), e);
            }
        }
    }
}
