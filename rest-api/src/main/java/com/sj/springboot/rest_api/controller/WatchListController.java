// src/main/java/com/portfolio/controller/WatchListController.java
package com.sj.springboot.rest_api.controller;

import com.sj.springboot.rest_api.entity.WatchList;
import com.sj.springboot.rest_api.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RestController
@RequestMapping("/watchlist")
public class WatchListController {
    
    @Autowired
    private WatchListService watchListService;
    
    @GetMapping
    public List<WatchList> watchListPage(Model model) {
        List<WatchList> watchList = watchListService.getAllWatchListItems();
        model.addAttribute("watchList", watchList);
        return watchList;
    }
    
    @PostMapping("/add")
    public List<WatchList> addToWatchList(@RequestParam String ticker, Model model) {
        System.out.println("in the method");
        watchListService.addToWatchList(ticker.toUpperCase());
        return watchListPage(model);
    }
    
    @PostMapping("/remove")
    public List<WatchList> removeFromWatchList(@RequestParam String ticker, Model model) {
        watchListService.removeFromWatchList(ticker);
        return watchListPage(model);
    }
}
