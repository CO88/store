package com.cobb.core.application.handler.command;

import com.cobb.base.error.exception.NotFoundException;
import com.cobb.core.domain.entity.Brand;
import com.cobb.core.domain.entity.Category;
import com.cobb.core.domain.entity.Product;
import com.cobb.core.domain.port.BrandPort;
import com.cobb.core.domain.port.CategoryPort;
import com.cobb.core.domain.service.CategoryPriceSummaryUpdator;
import com.cobb.core.domain.service.LowestTotalUpdator;
import com.cobb.core.domain.service.ProductCreator;
import com.cobb.core.dto.ProductDto;
import com.cobb.core.dto.mapper.ProductMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateProudctHandler {

  private final CategoryPort categoryPort;
  private final BrandPort brandPort;

  private final ProductCreator productCreator;
  private final CategoryPriceSummaryUpdator categoryPriceSummaryUpdator;
  private final LowestTotalUpdator lowestTotalUpdator;

  private final ProductMapper productMapper;

  @Transactional
  public List<ProductDto> execute(List<ProductDto> productDtoList) {
    List<Product> createdProducts = new ArrayList<>();

    for (ProductDto productDto : productDtoList) {
      Brand brand = brandPort.findByName(productDto.brand()).orElseThrow(NotFoundException::new);
      Category category = categoryPort.findByName(productDto.category()).orElseThrow(NotFoundException::new);

      Product newProduct = productCreator.execute(brand, category, productDto.price());
      createdProducts.add(newProduct);
    }

    // 여러 개의 상품을 한 번에 처리하는 메서드를 호출
    lowestTotalUpdator.batchCreateAndUpdate(createdProducts);
    categoryPriceSummaryUpdator.batchCreateAndUpdate(createdProducts);

    // 모든 상품을 ProductDto로 변환하여 반환
    return createdProducts.stream()
            .map(productMapper::map)
            .toList();
  }
}
