// src/main/java/com/sj/springboot/rest_api/repository/PortfolioRepository.java
package com.sj.springboot.rest_api.repository;

import com.sj.springboot.rest_api.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    // Already existing
    List<Portfolio> findByStockTicker(String stockTicker);

    @Query("SELECT DISTINCT p.stockTicker FROM Portfolio p")
    List<String> findDistinctStockTickers();

    // 🔹 NEW: Get a single portfolio record for a ticker
    Optional<Portfolio> findFirstByStockTicker(String stockTicker);

    // 🔹 NEW: Check total quantity for a ticker
    @Query("SELECT SUM(p.volume) FROM Portfolio p WHERE p.stockTicker = :stockTicker")
    Integer getTotalVolumeForTicker(String stockTicker);

    // 🔹 NEW: Get total investment value (optional)
    @Query("SELECT SUM(p.priceOfBuying * p.volume) FROM Portfolio p")
    BigDecimal getTotalInvestmentValue();

    // 🔹 NEW: Delete all holdings for a ticker (used when selling all shares)
    void deleteByStockTicker(String stockTicker);
}
