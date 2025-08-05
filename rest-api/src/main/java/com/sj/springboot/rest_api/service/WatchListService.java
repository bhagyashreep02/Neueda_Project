// src/main/java/com/portfolio/service/WatchListService.java
package com.sj.springboot.rest_api.service;

import com.sj.springboot.rest_api.entity.WatchList;
import com.sj.springboot.rest_api.repository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class WatchListService {


    @Autowired
    private WatchListRepository watchListRepository;
    
    public List<WatchList> getAllWatchListItems() {
        return watchListRepository.findAll();
    }
    
    public void addToWatchList(String ticker, Double price) {
        if (!watchListRepository.existsByStockTicker(ticker)) {
            WatchList watchList = new WatchList(ticker, LocalDateTime.now());
            watchListRepository.save(watchList);
        }
    }
    
    public void removeFromWatchList(String ticker) {
        watchListRepository.deleteByStockTicker(ticker);
    }
}