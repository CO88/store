package com.cobb.core.domain.port;

import com.cobb.core.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandPort extends JpaRepository<Brand, Long> {
  boolean existsByName(String name);

  Optional<Brand> findByName(String name);
}
