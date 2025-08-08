package com.sj.springboot.rest_api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Service
public class StockFetcherService {

    private static final Logger log = LoggerFactory.getLogger(StockFetcherService.class);

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${alphavantage.api.key}")
    private String apiKey;

    /**
     * Returns the current stock price from AlphaVantage (GLOBAL_QUOTE) or null on error.
     * Note: returns Double to match current TransactServiceImpl usage.
     */
    public Double getStockPrice(String symbol) {
        if (symbol == null || symbol.isBlank()) return null;
        try {
            String url = String.format(
                    "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
                    symbol, apiKey
            );

            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    log.warn("AlphaVantage request failed: code {}", response.code());
                    return null;
                }
                String body = response.body() != null ? response.body().string() : null;
                if (body == null) return null;
                JsonNode root = mapper.readTree(body);
                JsonNode quote = root.path("Global Quote");
                if (quote.isMissingNode() || quote.size() == 0) {
                    log.warn("AlphaVantage returned no Global Quote for {}: {}", symbol, body);
                    return null;
                }
                String priceStr = quote.path("05. price").asText(null);
                if (priceStr == null) return null;
                return Double.valueOf(priceStr);
            }
        } catch (Exception e) {
            log.error("Error fetching price for {}: {}", symbol, e.getMessage());
            return null;
        }
    }
}