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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RestController
//@Controller
//@RequestMapping("/orders")
//public class OrdersController {
//
//    @Autowired
//    private OrdersService ordersService;
//
//    @GetMapping
//    public List<Orders> ordersPage(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, Model model) {
//        List<Orders> orders;
//        if (date != null) {
//            orders = ordersService.getOrdersByDate(date);
//        } else {
//            orders = ordersService.getTodayOrders();
//        }
//
//        List<Orders> pendingOrders = ordersService.getPendingOrders();
//
//        model.addAttribute("orders", orders);
//        model.addAttribute("pendingOrders", pendingOrders);
//        model.addAttribute("selectedDate", date != null ? date : LocalDate.now());
//        return pendingOrders;
//    }
//}

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping
    public Map<String, Object> getOrders(
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        List<Orders> orders = (date != null)
                ? ordersService.getOrdersByDate(date)
                : ordersService.getTodayOrders();

        List<Orders> pendingOrders = ordersService.getPendingOrders();

        List<Orders> allOrders = ordersService.getAllOrders();

        Map<String, Object> response = new HashMap<>();
        response.put("allOrders", allOrders);
        response.put("orders", orders);
        response.put("pendingOrders", pendingOrders);
        response.put("selectedDate", date != null ? date : LocalDate.now());

        return response;
    }
}