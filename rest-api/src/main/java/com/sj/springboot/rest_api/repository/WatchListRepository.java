// src/main/java/com/portfolio/repository/WatchListRepository.java
package com.sj.springboot.rest_api.repository;

import com.sj.springboot.rest_api.entity.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList, Long> {
    boolean existsByStockTicker(String stockTicker);
    void deleteByStockTicker(String stockTicker);
}