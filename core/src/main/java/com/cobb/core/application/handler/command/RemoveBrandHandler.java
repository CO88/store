package com.cobb.core.application.handler.command;

import com.cobb.base.error.exception.NotFoundException;
import com.cobb.core.domain.entity.CategoryPriceSummary;
import com.cobb.core.domain.entity.Product;
import com.cobb.core.domain.port.BrandPort;
import com.cobb.core.domain.port.CategoryPriceSummaryPort;
import com.cobb.core.domain.port.ProductPort;
import com.cobb.core.domain.service.CategoryPriceSummaryUpdator;
import com.cobb.core.domain.service.LowestTotalUpdator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RemoveBrandHandler {

  private final ProductPort productPort;
  private final BrandPort brandPort;
  private final CategoryPriceSummaryPort categoryPriceSummaryPort;

  private final CategoryPriceSummaryUpdator categoryPriceSummaryUpdator;
  private final LowestTotalUpdator lowestTotalUpdator;


  @Transactional
  public void execute(Long brandId) {
    brandPort.findById(brandId).orElseThrow(NotFoundException::new);
    List<CategoryPriceSummary> categoryPriceSummaries = categoryPriceSummaryPort.findByBrandId(brandId);
    lowestTotalUpdator.remove(brandId);
    categoryPriceSummaryUpdator.remove(categoryPriceSummaries);

    List<Product> products = productPort.findByBrand_Id(brandId);
    productPort.deleteAll(products);

    categoryPriceSummaryUpdator.update(categoryPriceSummaries);
    categoryPriceSummaryPort.saveAll(categoryPriceSummaries);
  }
}
