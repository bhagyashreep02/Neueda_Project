// src/main/java/com/portfolio/repository/OrdersRepository.java
package com.sj.springboot.rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sj.springboot.rest_api.entity.Orders;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<Orders> findByStatusCode(String statusCode);
}