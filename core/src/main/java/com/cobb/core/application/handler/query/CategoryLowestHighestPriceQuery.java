package com.cobb.core.application.handler.query;

import com.cobb.base.error.exception.NotFoundException;
import com.cobb.core.domain.entity.CategoryPriceSummary;
import com.cobb.core.domain.port.CategoryPriceSummaryPort;
import com.cobb.core.dto.CategoryLowestHighestPriceDto;
import com.cobb.core.dto.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryLowestHighestPriceQuery {

  private final CategoryPriceSummaryPort categoryPriceSummaryPort;

  private final ProductMapper productMapper;

  public CategoryLowestHighestPriceDto execute(String categoryName) {
    CategoryPriceSummary categoryPriceSummary = categoryPriceSummaryPort
            .findByCategory_Name(categoryName)
            .orElseThrow(NotFoundException::new);

    return CategoryLowestHighestPriceDto.builder()
            .categoryName(categoryPriceSummary.getCategory().getName())
            .lowestProduct(productMapper.map(categoryPriceSummary.getMinProduct()))
            .highestProduct(productMapper.map(categoryPriceSummary.getMaxProduct()))
            .build();
  }
}
