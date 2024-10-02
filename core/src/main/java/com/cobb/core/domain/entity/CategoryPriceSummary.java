package com.cobb.core.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Getter
@Entity(name = "category_price_summaries")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class CategoryPriceSummary {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "min_product_id")
  private Product minProduct;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "max_product_id")
  private Product maxProduct;

  @Version
  private Long version;

  public static CategoryPriceSummary create(Category category, Product product) {
    CategoryPriceSummary summary = new CategoryPriceSummary();
    summary.category = category;
    summary.maxProduct = product;
    summary.minProduct = product;
    return summary;
  }

  public boolean containsMinProduct(Product product) {
    return this.minProduct != null && Objects.equals(this.minProduct.getId(), product.getId());
  }

  public boolean containsMaxProduct(Product product) {
    return this.maxProduct != null && Objects.equals(this.maxProduct.getId(), product.getId());
  }

  public boolean isMinEmpty() {
    return this.minProduct == null;
  }

  public boolean isMaxEmpty() {
    return this.maxProduct == null;
  }

  public void update(Product product) {
    if (minProduct == null || minProduct.getPrice() > product.getPrice()) {
      minProduct = product;
    }

    if (maxProduct == null || maxProduct.getPrice() < product.getPrice()) {
      maxProduct = product;
    }
  }
}
