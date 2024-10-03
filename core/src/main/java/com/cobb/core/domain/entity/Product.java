package com.cobb.core.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_brand_id", columnList = "brand_id"),
                @Index(name = "idx_category_id", columnList = "category_id")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Product implements Cloneable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer price;

  @ManyToOne
  @JoinColumn(name = "brand_id")
  private Brand brand;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  public static Product create(Brand brand, Category category, Integer price) {
    Product product = new Product();
    product.brand = brand;
    product.category = category;
    product.price = price;
    return product;
  }

  public void update(Brand brand, Category category, Integer price) {
    if (brand != null) {
      this.brand = brand;
    }

    if (category != null) {
      this.category = category;
    }

    if (price != null) {
      this.price = price;
    }
  }

  public Product clone() {
    try {
      return (Product) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }
}
