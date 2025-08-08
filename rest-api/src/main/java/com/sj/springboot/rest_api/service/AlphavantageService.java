package com.sj.springboot.rest_api.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class AlphavantageService {

    private static final String API_KEY = "YOUR_ALPHA_VANTAGE_KEY";
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    private final OkHttpClient client = new OkHttpClient();

    public List<Double> getLastNDaysOpenPrices(String symbol, int days) {
        List<Double> prices = new ArrayList<>();
        try {
            String url = String.format(
                    "%s?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s",
                    BASE_URL, symbol, API_KEY
            );

            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                String jsonData = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonData);

                if (!jsonObject.has("Time Series (Daily)")) {
                    System.out.println("Error: API response does not contain 'Time Series (Daily)'");
                    return prices; // empty list
                }

                JSONObject timeSeries = jsonObject.getJSONObject("Time Series (Daily)");
                List<String> dates = new ArrayList<>(timeSeries.keySet());
                Collections.sort(dates, Collections.reverseOrder()); // newest first

                for (int i = 0; i < Math.min(days, dates.size()); i++) {
                    String date = dates.get(i);
                    double openPrice = timeSeries.getJSONObject(date).getDouble("1. open");
                    prices.add(openPrice);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prices;
    }
}
