package com.cobb.core.domain.service;

import com.cobb.base.error.exception.NotFoundException;
import com.cobb.core.domain.entity.CategoryPriceSummary;
import com.cobb.core.domain.entity.Product;
import com.cobb.core.domain.port.CategoryPriceSummaryPort;
import com.cobb.core.domain.port.ProductPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryPriceSummaryUpdator {

  private final CategoryPriceSummaryPort categoryPriceSummaryPort;
  private final ProductPort productPort;

  public void createAndUpdate(Product product) {
    CategoryPriceSummary categoryPriceSummary = categoryPriceSummaryPort
            .findByCategory_Id(product.getCategory().getId())
            .orElse(CategoryPriceSummary.create(product.getCategory(), product));

    categoryPriceSummary.update(product);
    categoryPriceSummaryPort.save(categoryPriceSummary);
  }

  public void batchCreateAndUpdate(List<Product> products) {
    Map<Long, CategoryPriceSummary> categorySummaryMap = new HashMap<>();

    for (Product product : products) {
      CategoryPriceSummary categoryPriceSummary = categorySummaryMap
              .computeIfAbsent(product.getCategory().getId(),
                      id -> categoryPriceSummaryPort.findByCategory_Id(id)
                              .orElse(CategoryPriceSummary.create(product.getCategory(), product)));

      categoryPriceSummary.update(product);
      categoryPriceSummaryPort.save(categoryPriceSummary);
    }
  }

  /**
   * 카테고리별 최저가, 최고가를 업데이트합니다.
   *
   * @param oldProduct 는 제거될 대상 상품
   */
  public void removeAndUpdate(Product oldProduct) {
    CategoryPriceSummary categoryPriceSummary = categoryPriceSummaryPort
            .findByCategory_Id(oldProduct.getCategory().getId())
            .orElseThrow(NotFoundException::new);

    remove(categoryPriceSummary, oldProduct);
    update(categoryPriceSummary);

    categoryPriceSummaryPort.save(categoryPriceSummary);
  }

  public void remove(CategoryPriceSummary categoryPriceSummary, Product oldProduct) {
    if (categoryPriceSummary.containsMinProduct(oldProduct)) {
      categoryPriceSummary.setMinProduct(null);
    }

    if (categoryPriceSummary.containsMaxProduct(oldProduct)) {
      categoryPriceSummary.setMaxProduct(null);
    }
  }

  public void update(CategoryPriceSummary categoryPriceSummary) {
    if (categoryPriceSummary.isMinEmpty()) {
      Product min = productPort.findTopByCategory_IdOrderByPriceAsc(categoryPriceSummary.getCategory().getId());
      categoryPriceSummary.update(min);
    }

    if (categoryPriceSummary.isMaxEmpty()) {
      Product max = productPort.findTopByCategory_IdOrderByPriceDesc(categoryPriceSummary.getCategory().getId());
      categoryPriceSummary.update(max);
    }
  }

  public void remove(List<CategoryPriceSummary> categoryPriceSummaries) {
    categoryPriceSummaries.forEach(categoryPriceSummary -> {
      categoryPriceSummary.setMinProduct(null);
      categoryPriceSummary.setMaxProduct(null);
    });
  }

  public void update(List<CategoryPriceSummary> categoryPriceSummaries) {
    categoryPriceSummaries.forEach(this::update);
  }
}

