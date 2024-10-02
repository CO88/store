package com.cobb.core.domain.service;

import com.cobb.core.domain.entity.Brand;
import com.cobb.core.domain.entity.Category;
import com.cobb.core.domain.entity.Product;
import com.cobb.core.domain.port.ProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCreator {

  private final ProductPort productPort;

  public Product execute(Brand brand, Category category, Integer price) {
    Product newProduct = Product.create(brand, category, price);
    return productPort.save(newProduct);
  }
}
