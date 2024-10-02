package com.cobb.core.application.handler.query;

import com.cobb.core.domain.entity.CategoryPriceSummary;
import com.cobb.core.domain.port.CategoryPriceSummaryPort;
import com.cobb.core.dto.CategoryLowestPriceDto;
import com.cobb.core.dto.ProductDto;
import com.cobb.core.dto.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryLowestPriceQuey {

  private final CategoryPriceSummaryPort categoryPriceSummaryPort;

  private final ProductMapper productMapper;

  public CategoryLowestPriceDto execute() {
    List<CategoryPriceSummary> categoryPriceSummaries = categoryPriceSummaryPort.findAll();

    List<ProductDto> productDtos = new ArrayList<>();
    int totalPrice = 0;
    for (CategoryPriceSummary categoryPriceSummary : categoryPriceSummaries) {
      productDtos.add(productMapper.map(categoryPriceSummary.getMinProduct()));
      totalPrice += categoryPriceSummary.getMinProduct().getPrice();
    }

    return CategoryLowestPriceDto.builder()
            .productDtos(productDtos)
            .total(totalPrice)
            .build();
  }
}
