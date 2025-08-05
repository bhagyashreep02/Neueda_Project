// src/main/java/com/portfolio/repository/PortfolioRepository.java
package com.sj.springboot.rest_api.repository;

import com. sj.springboot.rest_api.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


// extends JPA repo and corresponds with the entity Portfolio
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByStockTicker(String stockTicker);

    // returns DISTINCT tickers for further calculations in portfolio (Current Holdings)
    @Query("SELECT DISTINCT p.stockTicker FROM Portfolio p")
    List<String> findDistinctStockTickers();
}