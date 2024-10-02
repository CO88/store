package com.cobb.core.domain.service;

import com.cobb.base.error.exception.NotFoundException;
import com.cobb.core.domain.entity.LowestTotal;
import com.cobb.core.domain.entity.Product;
import com.cobb.core.domain.entity.ProductLowestTotal;
import com.cobb.core.domain.port.LowestTotalPort;
import com.cobb.core.domain.port.ProductLowestTotalPort;
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
public class LowestTotalUpdator {

  private final LowestTotalPort lowestTotalPort;
  private final ProductLowestTotalPort productLowestTotalPort;
  private final ProductPort productPort;

  /**
   * 기존의 브랜드별 카테고리의 총합을 업데이트합니다. (추가)
   *
   * @param product 추가되어 업데이트할 상품
   */
  public void createAndUpdate(Product product) {
    LowestTotal lowestTotal = lowestTotalPort
            .findByBrand_Id(product.getBrand().getId())
            .orElse(LowestTotal.create(product.getBrand()));

    lowestTotal.calculate(product);
    lowestTotalPort.save(lowestTotal);
  }

  public void batchCreateAndUpdate(List<Product> products) {
    Map<Long, LowestTotal> lowestTotalMap = new HashMap<>();

    for (Product product : products) {
      LowestTotal lowestTotal = lowestTotalMap
              .computeIfAbsent(product.getBrand().getId(),
                      id -> lowestTotalPort.findByBrand_Id(id)
                              .orElse(LowestTotal.create(product.getBrand())));

      lowestTotal.calculate(product);
      lowestTotalPort.save(lowestTotal);
    }
  }

  /**
   * 브랜드별 카테고리의 총합을 재계산합니다. (제거)
   *
   * @param oldProduct 제거되어 업데이트할 상품
   */
  public void removeAndUpdate(Product oldProduct) {
    LowestTotal lowestTotal = lowestTotalPort
            .findByBrand_Id(oldProduct.getBrand().getId())
            .orElseThrow(NotFoundException::new);

    // 기존 상품이 브랜드별 최저 총합에 포함되어있으면 제거한다.
    remove(lowestTotal, oldProduct);

    // 제거된 상품의 브랜드, 카테고리를 업데이트 시켜준다.
    update(lowestTotal, oldProduct);
    lowestTotalPort.save(lowestTotal);
  }

  public void remove(LowestTotal lowestTotal, Product oldProduct) {
    if (lowestTotal.contains(oldProduct)) {
      ProductLowestTotal productLowestTotal = lowestTotal.remove(oldProduct);
      productLowestTotalPort.delete(productLowestTotal);
    }
  }

  public void update(LowestTotal lowestTotal, Product oldProduct) {
    Product p = productPort.findTopByBrand_IdAndCategory_IdOrderByPrice(oldProduct.getBrand().getId(), oldProduct.getCategory().getId());
    if (p != null) {
      lowestTotal.calculate(p);
    }
  }

  public void remove(Long brandId) {
    LowestTotal lowestTotal = lowestTotalPort.findByBrand_Id(brandId).orElseThrow(NotFoundException::new);
    lowestTotalPort.delete(lowestTotal);
  }
}
