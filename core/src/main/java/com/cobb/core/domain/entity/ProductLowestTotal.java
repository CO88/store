package com.cobb.core.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name = "product_lowest_totals")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductLowestTotal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lowest_total_id")
  private LowestTotal lowestTotal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  public static ProductLowestTotal create(LowestTotal lowestTotal, Product product) {
    ProductLowestTotal productLowestTotal = new ProductLowestTotal();
    productLowestTotal.lowestTotal = lowestTotal;
    productLowestTotal.product = product;
    return productLowestTotal;
  }

  public void update(Product product) {
    this.product = product;
  }
}
