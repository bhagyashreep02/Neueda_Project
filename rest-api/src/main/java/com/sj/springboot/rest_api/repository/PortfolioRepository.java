// src/main/java/com/portfolio/repository/PortfolioRepository.java
package com.sj.springboot.rest_api.repository;

import com. sj.springboot.rest_api.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByStockTicker(String stockTicker);
    
    @Query("SELECT DISTINCT p.stockTicker FROM Portfolio p")
    List<String> findDistinctStockTickers();
}