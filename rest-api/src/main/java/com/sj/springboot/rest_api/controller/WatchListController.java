// src/main/java/com/sj/springboot/rest_api/controller/WatchListController.java
package com.sj.springboot.rest_api.controller;

import com.sj.springboot.rest_api.entity.WatchList;
import com.sj.springboot.rest_api.service.StockFetcherService;
import com.sj.springboot.rest_api.service.WatchListService;
import com.sj.springboot.rest_api.wrapper.StockWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import yahoofinance.Stock;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/watchlist")
public class WatchListController {

    @Autowired
    private StockFetcherService stockFetcherService;

    @Autowired
    private WatchListService watchListService;

    @GetMapping
    public List<StockWrapper> watchListPage(Model model) {
        List<WatchList> watchList = watchListService.getAllWatchListItems();
    
        // for (WatchList item : watchList) {
        //     System.out.println(item.toString());
        // }

        List<StockWrapper> enrichedList = new ArrayList<>();

        for (WatchList item : watchList) {
            StockWrapper stockData = stockFetcherService.findStock(item.getStockTicker());
            // if (stockData != null) {
                enrichedList.add(stockData);
            // }
        }

        model.addAttribute("watchList", enrichedList);
        return enrichedList;
    }

    @PostMapping("/add")
    public List<StockWrapper> addToWatchList(@RequestParam String ticker, Model model) {
        String symbol = ticker.toUpperCase();

        // 1. Fetch live stock info
        StockWrapper stockWrapper = stockFetcherService.findStock(symbol);
        if (stockWrapper == null) {
            throw new RuntimeException("Invalid or unavailable ticker: " + symbol);
        }

        // 2. Create WatchList item from stock data
        WatchList watchListItem = new WatchList();
        // watchListItem.setStockTicker(symbol);
        // watchListItem.setStockName(stockWrapper.getName());
        // watchListItem.setCurrentPrice(stockWrapper.getPrice());
        // watchListItem.setCurrency(stockWrapper.getCurrency());

        // 3. Save to DB
        watchListService.addToWatchList(ticker.toUpperCase());

        // 4. Return updated watchlist with enriched stock data
        return watchListPage(model);
    }

    @PostMapping("/remove")
    public List<StockWrapper> removeFromWatchList(@RequestParam String ticker, Model model) {
        watchListService.removeFromWatchList(ticker.toUpperCase());
        return watchListPage(model);
    }
}

