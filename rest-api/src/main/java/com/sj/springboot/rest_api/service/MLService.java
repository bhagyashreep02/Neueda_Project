package com.sj.springboot.rest_api.service;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MLService {

    public double predictNextPrice(List<Double> prices) {
        if (prices == null || prices.size() < 2) {
            return Double.NaN; // Not enough data
        }

        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < prices.size(); i++) {
            regression.addData(i, prices.get(i));
        }

        return regression.predict(prices.size()); // next day's index
    }
}
