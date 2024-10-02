package com.cobb.core.domain.port;

import com.cobb.core.domain.entity.ProductLowestTotal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLowestTotalPort extends JpaRepository<ProductLowestTotal, Long> {
}
