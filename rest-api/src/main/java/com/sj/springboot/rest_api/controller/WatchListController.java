package com.sj.springboot.rest_api.controller;

import com.sj.springboot.rest_api.entity.WatchList;
import com.sj.springboot.rest_api.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/watchlist")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @Autowired
    private StockFetcherService stockFetcherService;

    @GetMapping
    public List<Map<String, Object>> watchListPage() {
        List<WatchList> watchList = watchListService.getAllWatchListItems();

        List<Map<String, Object>> enriched = new ArrayList<>();

        for (WatchList item : watchList) {
            Map<String, Object> stockData = new HashMap<>();
            stockData.put("ticker", item.getStockTicker());

            Double price = stockFetcherService.getStockPrice(item.getStockTicker());
            stockData.put("price", price != null ? price : "Unavailable");

            enriched.add(stockData);
        }

        return enriched;
    }

    @PostMapping("/add")
    public List<Map<String, Object>> addToWatchList(@RequestParam String ticker) {
        String symbol = ticker.toUpperCase();

        Double price = stockFetcherService.getStockPrice(symbol);

        // Save ticker and price to DB
        watchListService.addToWatchList(symbol, price);

        return watchListPage();
    }


    @PostMapping("/remove")
    public List<Map<String, Object>> removeFromWatchList(@RequestParam String ticker) {
        watchListService.removeFromWatchList(ticker.toUpperCase());
        return watchListPage();
    }
}
