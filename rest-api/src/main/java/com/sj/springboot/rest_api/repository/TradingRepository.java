package com.sj.springboot.rest_api.repository;

import com.sj.springboot.rest_api.entity.Trading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradingRepository extends JpaRepository<Trading, Long> {
}