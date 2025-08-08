package com.sj.springboot.rest_api.service;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MLService {

    public double predictNextPrice(List<Double> prices) {
        if (prices == null || prices.size() < 5) {
            return Double.NaN; // not enough data
        }

        List<Double> diffSeries = new ArrayList<>();
        for (int i = 1; i < prices.size(); i++) {
            diffSeries.add(prices.get(i) - prices.get(i - 1));
        }

        int n = diffSeries.size() - 1;
        double[] y = new double[n];
        double[][] x = new double[n][1];

        for (int i = 0; i < n; i++) {
            y[i] = diffSeries.get(i + 1);
            x[i][0] = diffSeries.get(i);
        }

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(y, x);

        double[] beta = regression.estimateRegressionParameters();

        double lastDiff = diffSeries.get(diffSeries.size() - 1);
        double forecastDiff = beta[0] + beta[1] * lastDiff;

        double lastActualPrice = prices.get(prices.size() - 1);
        return lastActualPrice + forecastDiff;
    }
}
