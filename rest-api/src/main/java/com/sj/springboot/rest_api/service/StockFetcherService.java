package com.sj.springboot.rest_api.service;

import com.sj.springboot.rest_api.wrapper.StockWrapper;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StockFetcherService {

    private static final Map<String, StockWrapper> cache = new ConcurrentHashMap<>();

    public StockWrapper findStock(String symbol) {
        symbol = symbol.toUpperCase();

        try {
            // -->here is the problem
            System.out.println("llllllllllllllllll");
            // Stock stock = YahooFinance.get(symbol);
            System.out.println("mmmmmmmmmmmmmmmmm");
            // -->here is the problem

            if (YahooFinance.get(symbol) == null || YahooFinance.get(symbol).getQuote().getPrice() == null) {
                System.out.println("nnnnnnnnnnnnnnnnnn");
                System.out.println("Stock data not found for: " + symbol);
                return null;
            }

            StockWrapper wrapper = new StockWrapper(YahooFinance.get(symbol));
            cache.put(symbol, wrapper);
            return wrapper;

        } catch (Exception e) {
            System.out.println("Error fetching stock data for " + symbol + ": " + e.getMessage());
            return null;
        }
    }
}
