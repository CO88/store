package com.cobb.api.integration.base;

import com.cobb.core.dto.BrandDto;
import com.cobb.core.dto.ProductDto;
import net.datafaker.Faker;

public class TestFactory {

  private static final Faker faker = new Faker();

  public static final String PRODUCT_API_ENDPOINT = "/product";
  public static final String BRAND_API_ENDPOINT = "/brand";
  public static final String LOWEST_PRICE_API_ENDPOINT = "/categories/lowest-price";

  public ProductDto generateProductDto(String brandName, String categoryName, Integer price) {
    return ProductDto.builder()
            .brand(brandName)
            .category(categoryName)
            .price(price)
            .build();
  }

  public ProductDto generateAbnormalProductDto() {
    return ProductDto.builder()
            .brand("Z")
            .category("상의")
            .price(faker.number().numberBetween(1000, 45000))
            .build();
  }

  public BrandDto generateBrandDto() {
    return BrandDto.builder()
            .name("O")
            .build();
  }

  public BrandDto generateAbnormalBrandDto() {
    return BrandDto.builder()
            .name("A")
            .build();
  }
}
