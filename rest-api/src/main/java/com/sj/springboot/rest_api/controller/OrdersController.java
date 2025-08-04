// src/main/java/com/portfolio/controller/OrdersController.java
package com.sj.springboot.rest_api.controller;

import com.sj.springboot.rest_api.entity.Orders;
import com.sj.springboot.rest_api.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@Controller
@RequestMapping("/orders")
public class OrdersController {
    
    @Autowired
    private OrdersService ordersService;
    
    @GetMapping
    public List<Orders> ordersPage(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                            Model model) {
        List<Orders> orders;
        if (date != null) {
            orders = ordersService.getOrdersByDate(date);
        } else {
            orders = ordersService.getTodayOrders();
        }
        
        List<Orders> pendingOrders = ordersService.getPendingOrders();
        
        model.addAttribute("orders", orders);
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("selectedDate", date != null ? date : LocalDate.now());
        return pendingOrders;
    }
}