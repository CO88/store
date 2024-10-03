package com.cobb.core.application.handler.query;

import com.cobb.base.error.exception.NotFoundException;
import com.cobb.core.domain.entity.Product;
import com.cobb.core.domain.port.ProductPort;
import com.cobb.core.dto.ProductDto;
import com.cobb.core.dto.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQuery {

  private final ProductPort productPort;
  private final ProductMapper productMapper;

  public List<ProductDto> gets() {
    List<Product> products = productPort.findAll();

    return products.stream().map(productMapper::map).toList();
  }

  public ProductDto getById(Long id) {
    Product product = productPort.findById(id).orElseThrow(NotFoundException::new);
    return productMapper.map(product);
  }
}
