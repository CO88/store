package com.cobb.core.domain.port;

import com.cobb.core.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryPort extends JpaRepository<Category, Long> {
  Optional<Category> findByName(String name);
}
