// src/main/java/com/portfolio/service/OrdersService.java
package com.sj.springboot.rest_api.service;

import com.sj.springboot.rest_api.entity.Orders;
import com.sj.springboot.rest_api.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class OrdersService {
    
    @Autowired
    private OrdersRepository ordersRepository;
    
    public List<Orders> getTodayOrders() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        return ordersRepository.findByTimestampBetween(startOfDay, endOfDay);
    }
    
    public List<Orders> getOrdersByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return ordersRepository.findByTimestampBetween(startOfDay, endOfDay);
    }
    
    public List<Orders> getPendingOrders() {
        return ordersRepository.findByStatusCode("PENDING");
    }
}