package com.cobb.core.domain.port;

import com.cobb.core.domain.entity.CategoryPriceSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryPriceSummaryPort extends JpaRepository<CategoryPriceSummary, Long> {
  Optional<CategoryPriceSummary> findByCategory_Id(Long categoryId);

  Optional<CategoryPriceSummary> findByCategory_Name(String name);

  @Query("SELECT DISTINCT cps FROM category_price_summaries cps " +
          "LEFT JOIN cps.minProduct minP " +
          "LEFT JOIN cps.maxProduct maxP " +
          "WHERE minP.brand.id = :brandId OR maxP.brand.id = :brandId")
  List<CategoryPriceSummary> findByBrandId(Long brandId);
}
