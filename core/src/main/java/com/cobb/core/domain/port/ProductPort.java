package com.cobb.core.domain.port;

import com.cobb.core.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPort extends JpaRepository<Product, Long> {
  Product findTopByBrand_IdAndCategory_IdOrderByPrice(Long brandId, Long categoryId);

  Product findTopByCategory_IdOrderByPriceDesc(Long categoryId);

  Product findTopByCategory_IdOrderByPriceAsc(Long categoryId);

  List<Product> findByBrand_Id(Long brandId);

}
