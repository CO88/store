package com.cobb.api.adapter.in.http;

import com.cobb.core.application.handler.command.CreateProudctHandler;
import com.cobb.core.application.handler.command.RemoveProductHandler;
import com.cobb.core.application.handler.command.UpdateProductHandler;
import com.cobb.core.application.handler.query.ProductQuery;
import com.cobb.core.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

  private final CreateProudctHandler createProudctHandler;
  private final UpdateProductHandler updateProductHandler;
  private final RemoveProductHandler removeProductHandler;

  private final ProductQuery productQuery;

  @GetMapping("/products")
  public List<ProductDto> gets() {
    return productQuery.gets();
  }

  @GetMapping("/products/{id}")
  public ProductDto get(@PathVariable Long id) {
    return productQuery.getById(id);
  }

  @PostMapping("/products")
  public List<ProductDto> create(@RequestBody List<ProductDto> productDtos) {
    return createProudctHandler.execute(productDtos);
  }

  @PatchMapping("/products/{id}")
  public ProductDto update(@PathVariable Long id, @RequestBody ProductDto productDto) {
    return updateProductHandler.execute(id, productDto);
  }

  @DeleteMapping("/products/{id}")
  public boolean delete(@PathVariable Long id) {
    removeProductHandler.execute(id);
    return true;
  }
}
