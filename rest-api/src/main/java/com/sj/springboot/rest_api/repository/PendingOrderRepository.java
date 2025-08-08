package com.sj.springboot.rest_api.repository;

import com.sj.springboot.rest_api.entity.PendingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendingOrderRepository extends JpaRepository<PendingOrder, Long> {

    // Find all pending orders for a ticker
    List<PendingOrder> findByTicker(String ticker);

    // Find all pending BUY orders
    List<PendingOrder> findByOrderType(String orderType);

    // Find all orders matching ticker and type
    List<PendingOrder> findByTickerAndOrderType(String ticker, String orderType);
}
