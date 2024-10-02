package com.cobb.core.application.handler.command;

import com.cobb.base.error.exception.NotFoundException;
import com.cobb.core.domain.entity.Product;
import com.cobb.core.domain.port.ProductPort;
import com.cobb.core.domain.service.CategoryPriceSummaryUpdator;
import com.cobb.core.domain.service.LowestTotalUpdator;
import com.cobb.core.domain.service.ProductUpdator;
import com.cobb.core.dto.ProductDto;
import com.cobb.core.dto.mapper.ProductMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProductHandler {

  private final ProductPort productPort;

  private final ProductUpdator productUpdator;
  private final CategoryPriceSummaryUpdator categoryPriceSummaryUpdator;
  private final LowestTotalUpdator lowestTotalUpdator;

  private final ProductMapper productMapper;


  @Transactional
  public ProductDto execute(Long targetProductId, ProductDto productDto) {
    Product targetProduct = productPort.findById(targetProductId).orElseThrow(NotFoundException::new);
    Product oldProduct = targetProduct.clone();
    Product newProduct = productUpdator.execute(targetProduct, productDto.brand(), productDto.category(), productDto.price());

    lowestTotalUpdator.removeAndUpdate(oldProduct);
    lowestTotalUpdator.createAndUpdate(newProduct);
    categoryPriceSummaryUpdator.removeAndUpdate(oldProduct);
    categoryPriceSummaryUpdator.createAndUpdate(newProduct);

    return productMapper.map(newProduct);
  }
}
