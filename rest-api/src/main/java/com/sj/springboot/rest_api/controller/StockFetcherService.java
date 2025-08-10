package com.sj.springboot.rest_api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

@Service
public class StockFetcherService {

    private static final String API_KEY = "PGRETQQ46NGNNBQ3";
    private static final OkHttpClient client = new OkHttpClient();

    public Double getStockPrice(String symbol) {
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol +
                "&apikey=" + API_KEY;

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("API request failed: " + response.code());
                return null;
            }

            String responseBody = response.body().string();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);

            String price = root.path("Global Quote").path("05. price").asText();
            return price.isEmpty() ? null : Double.parseDouble(price);

        } catch (Exception e) {
            System.out.println("Error fetching stock price: " + e.getMessage());
            return null;
        }
    }
}
