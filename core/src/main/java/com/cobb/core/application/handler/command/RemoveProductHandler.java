package com.cobb.core.application.handler.command;

import com.cobb.base.error.exception.NotFoundException;
import com.cobb.core.domain.entity.CategoryPriceSummary;
import com.cobb.core.domain.entity.LowestTotal;
import com.cobb.core.domain.entity.Product;
import com.cobb.core.domain.port.CategoryPriceSummaryPort;
import com.cobb.core.domain.port.LowestTotalPort;
import com.cobb.core.domain.port.ProductPort;
import com.cobb.core.domain.service.CategoryPriceSummaryUpdator;
import com.cobb.core.domain.service.LowestTotalUpdator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveProductHandler {

  private final ProductPort productPort;
  private final LowestTotalPort lowestTotalPort;
  private final CategoryPriceSummaryPort categoryPriceSummaryPort;

  private final CategoryPriceSummaryUpdator categoryPriceSummaryUpdator;
  private final LowestTotalUpdator lowestTotalUpdator;


  @Transactional
  public void execute(Long productId) {
    Product targetProduct = productPort.findById(productId).orElseThrow(NotFoundException::new);
    CategoryPriceSummary categoryPriceSummary = categoryPriceSummaryPort
            .findByCategory_Id(targetProduct.getCategory().getId())
            .orElseThrow(NotFoundException::new);
    LowestTotal lowestTotal = lowestTotalPort
            .findByBrand_Id(targetProduct.getBrand().getId())
            .orElseThrow(NotFoundException::new);


    lowestTotalUpdator.remove(lowestTotal, targetProduct);
    categoryPriceSummaryUpdator.remove(categoryPriceSummary, targetProduct);
    productPort.delete(targetProduct);
    lowestTotalUpdator.update(lowestTotal, targetProduct);
    categoryPriceSummaryUpdator.update(categoryPriceSummary);

    lowestTotalPort.save(lowestTotal);
    categoryPriceSummaryPort.save(categoryPriceSummary);
  }
}
