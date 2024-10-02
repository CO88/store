package com.cobb.core.domain.service;

import com.cobb.base.error.exception.NotFoundException;
import com.cobb.core.domain.entity.Brand;
import com.cobb.core.domain.entity.Category;
import com.cobb.core.domain.entity.Product;
import com.cobb.core.domain.port.BrandPort;
import com.cobb.core.domain.port.CategoryPort;
import com.cobb.core.domain.port.ProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductUpdator {

  private final CategoryPort categoryPort;
  private final BrandPort brandPort;
  private final ProductPort productPort;

  public Product execute(Product targetProduct, String brandName, String categoryName, Integer price) {
    Brand brand = null;
    Category category = null;

    if (brandName != null) {
      brand = brandPort.findByName(brandName).orElseThrow(NotFoundException::new);
    }

    if (categoryName != null) {
      category = categoryPort.findByName(categoryName).orElseThrow(NotFoundException::new);
    }

    targetProduct.update(brand, category, price);
    return productPort.save(targetProduct);
  }

}
