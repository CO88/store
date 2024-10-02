package com.cobb.core.dto.mapper;

import com.cobb.core.domain.entity.Product;
import com.cobb.core.dto.ProductDto;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

  public ProductDto map(Product product) {
    if (product == null) return null;
    return ProductDto.builder()
            .id(product.getId())
            .category(product.getCategory().getName())
            .brand(product.getBrand().getName())
            .price(product.getPrice())
            .build();
  }
}
