package com.sj.springboot.rest_api.controller;

import com.sj.springboot.rest_api.service.AlphavantageService;
import com.sj.springboot.rest_api.service.MLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class MLController {

    @Autowired
    private AlphavantageService alphaVantageService;

    @Autowired
    private MLService mlService;

    @GetMapping("/predict")
    public Map<String, Object> predict(@RequestParam String symbol) {
        List<Double> openPrices = alphaVantageService.getLastNDaysOpenPrices(symbol, 10);
        double prediction = mlService.predictNextPrice(openPrices);

        Map<String, Object> response = new HashMap<>();
        response.put("symbol", symbol);
        response.put("last_10_open_prices", openPrices);
        response.put("predicted_next_open_price", prediction);

        return response;
    }
}
