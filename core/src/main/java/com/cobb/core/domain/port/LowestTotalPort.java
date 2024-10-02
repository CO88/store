package com.cobb.core.domain.port;

import com.cobb.core.domain.entity.LowestTotal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LowestTotalPort extends JpaRepository<LowestTotal, Long> {

  LowestTotal findByOrderByTotal();

  Optional<LowestTotal> findByBrand_Id(Long id);

  Optional<LowestTotal> findByBrand_Name(String name);
}
