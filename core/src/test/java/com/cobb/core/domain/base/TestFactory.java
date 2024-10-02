package com.cobb.core.domain.base;

import com.cobb.core.domain.entity.Brand;
import com.cobb.core.domain.entity.Category;
import com.cobb.core.domain.entity.Product;

public class TestFactory {

  public Product generateProduct(String brandName, String categoryName, int price) {
    return Product.create(generateBrand(brandName), generateCategory(categoryName), price);
  }

  public Brand generateBrand(String name) {
    return Brand.create(name);
  }

  public Category generateCategory(String name) {
    return Category.create(name);
  }

}
