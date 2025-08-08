package com.sj.springboot.rest_api.repository;

import com.sj.springboot.rest_api.entity.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {
}
