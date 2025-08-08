package com.sj.springboot.rest_api.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlphavantageService {

    private static final Logger log = LoggerFactory.getLogger(AlphavantageService.class);

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${alphavantage.api.key}")
    private String apiKey;

    /**
     * Returns last N days' "open" prices for the ticker (most recent first).
     * Returns an empty list if API fails or data is missing.
     */
    public List<Double> getLastNDaysOpenPrices(String ticker, int days) {
        if (ticker == null || ticker.isBlank() || days <= 0) {
            log.warn("Invalid parameters: ticker='{}', days={}", ticker, days);
            return Collections.emptyList();
        }

        try {
            String url = String.format(
                    "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=%s&outputsize=compact&apikey=%s",
                    ticker.trim().toUpperCase(), apiKey
            );

            Request req = new Request.Builder().url(url).build();
            try (Response resp = client.newCall(req).execute()) {
                if (!resp.isSuccessful()) {
                    log.warn("AlphaVantage request failed [{}]: {}", resp.code(), resp.message());
                    return Collections.emptyList();
                }

                String body = resp.body() != null ? resp.body().string() : null;
                if (body == null || body.isBlank()) {
                    log.warn("AlphaVantage returned empty body for ticker {}", ticker);
                    return Collections.emptyList();
                }

                JsonNode root = mapper.readTree(body);

                // Handle API limit / note / error messages
                if (root.has("Note")) {
                    log.warn("AlphaVantage API limit reached: {}", root.path("Note").asText());
                    return Collections.emptyList();
                }
                if (root.has("Error Message")) {
                    log.warn("AlphaVantage error for {}: {}", ticker, root.path("Error Message").asText());
                    return Collections.emptyList();
                }

                JsonNode ts = root.path("Time Series (Daily)");
                if (ts.isMissingNode() || !ts.fieldNames().hasNext()) {
                    log.warn("No time series data for ticker {}. Full response: {}", ticker, body);
                    return Collections.emptyList();
                }

                // Sort dates in reverse order (latest first)
                List<String> dates = new ArrayList<>();
                ts.fieldNames().forEachRemaining(dates::add);
                dates.sort(Comparator.reverseOrder());

                return dates.stream()
                        .limit(days)
                        .map(d -> {
                            String openStr = ts.path(d).path("1. open").asText(null);
                            try {
                                return openStr != null ? Double.valueOf(openStr) : null;
                            } catch (NumberFormatException ex) {
                                log.warn("Invalid number format for date {}: {}", d, openStr);
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

            }
        } catch (Exception e) {
            log.error("Error fetching last N days open prices for {}: {}", ticker, e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
