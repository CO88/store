package com.cobb.core.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Entity
@Table(name = "lowest_totals")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LowestTotal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "brand_id")
  private Brand brand;

  private Integer total;

  @Version
  private Long version;

  @Getter
  @OneToMany(mappedBy = "lowestTotal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ProductLowestTotal> productLowestTotals = new ArrayList<>();

  public static LowestTotal create(Brand brand) {
    LowestTotal lowestTotal = new LowestTotal();
    lowestTotal.brand = brand;
    lowestTotal.total = 0;
    return lowestTotal;
  }

  public boolean contains(Product product) {
    return productLowestTotals.stream()
            .anyMatch(productLowestTotal -> Objects.equals(productLowestTotal.getProduct().getId(), product.getId()));
  }

  public ProductLowestTotal remove(Product product) {
    ProductLowestTotal found = productLowestTotals.stream()
            .filter(productLowestTotal -> Objects.equals(productLowestTotal.getProduct().getId(), product.getId()))
            .findFirst()
            .orElse(null);

    if (found != null) {
      productLowestTotals.remove(found);
    }
    updateTotal();
    return found;
  }

  public void calculate(Product product) {
    Optional<ProductLowestTotal> existingMatch = productLowestTotals.stream()
            .filter(pm -> pm.getProduct().getCategory().getName().equals(product.getCategory().getName()))
            .findFirst();

    existingMatch.ifPresentOrElse(
            pm -> {
              if (pm.getProduct().getPrice() > product.getPrice()) {
                pm.update(product);
              }
            },
            () -> productLowestTotals.add(ProductLowestTotal.create(this, product))
    );
    updateTotal();
  }

  public void removeAll() {
    productLowestTotals.clear();
    updateTotal();
  }

  private void updateTotal() {
    this.total = productLowestTotals.stream()
            .mapToInt(pm -> pm.getProduct().getPrice())
            .sum();
  }
}
