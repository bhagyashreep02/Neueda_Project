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
     * If API fails or data missing, returns an empty list.
     */
    public List<Double> getLastNDaysOpenPrices(String ticker, int days) {
        if (ticker == null || ticker.isBlank() || days <= 0) return Collections.emptyList();
        try {
            String url = String.format(
                    "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=%s&outputsize=compact&apikey=%s",
                    ticker, apiKey
            );

            Request req = new Request.Builder().url(url).build();
            try (Response resp = client.newCall(req).execute()) {
                if (!resp.isSuccessful()) {
                    log.warn("AlphaVantage timeseries failed: code {}", resp.code());
                    return Collections.emptyList();
                }
                String body = resp.body() != null ? resp.body().string() : null;
                if (body == null) return Collections.emptyList();
                JsonNode root = mapper.readTree(body);
                JsonNode ts = root.path("Time Series (Daily)");
                if (ts.isMissingNode()) {
                    log.warn("AlphaVantage returned no Time Series for {}: {}", ticker, body);
                    return Collections.emptyList();
                }

                // collect dates, sort descending (latest first)
                List<String> dates = new ArrayList<>();
                ts.fieldNames().forEachRemaining(dates::add);
                dates.sort(Comparator.reverseOrder());

                return dates.stream()
                        .limit(days)
                        .map(d -> {
                            JsonNode record = ts.path(d);
                            String open = record.path("1. open").asText(null);
                            try {
                                return open != null ? Double.valueOf(open) : null;
                            } catch (NumberFormatException ex) {
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("Error fetching last N days open prices for {}: {}", ticker, e.getMessage());
            return Collections.emptyList();
        }
    }
}