// src/main/java/com/portfolio/controller/HomeController.java
package com.sj.springboot.rest_api.controller;

import com.sj.springboot.rest_api.dto.PortfolioSummary;
import com.sj.springboot.rest_api.service.PortfolioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class HomeController {
    
    @Autowired
    private PortfolioService portfolioService;
    
    @GetMapping("/")
    public PortfolioSummary home(Model model) {
        PortfolioSummary summary = portfolioService.getPortfolioSummary();
        model.addAttribute("summary", summary);
        return summary;
    }
} 
