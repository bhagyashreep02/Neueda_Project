package com.sj.springboot.rest_api.repository;

import com.sj.springboot.rest_api.entity.PendingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PendingOrderRepository extends JpaRepository<PendingOrder, Long> {
    List<PendingOrder> findByTicker(String ticker);
    List<PendingOrder> findByType(String type);
    List<PendingOrder> findByTickerAndType(String ticker, String type);
}
