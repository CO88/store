package com.cobb.api.adapter.in.http;

import com.cobb.core.application.handler.query.CategoryLowestHighestPriceQuery;
import com.cobb.core.application.handler.query.CategoryLowestPriceQuey;
import com.cobb.core.dto.CategoryLowestHighestPriceDto;
import com.cobb.core.dto.CategoryLowestPriceDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryLowestPriceQuey lowestPriceQuey;
  private final CategoryLowestHighestPriceQuery lowestHighestPriceQuery;

  @Operation(
          summary = "카테고리별 최저가격 브랜드와 상품 가격, 총액을 조회"
  )
  @GetMapping("/categories/lowest-price-product")
  public CategoryLowestPriceDto get() {
    return lowestPriceQuey.execute();
  }

  @Operation(
          summary = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격 조회"
  )
  @GetMapping("/categories/{name}/lowest-highest-price")
  public CategoryLowestHighestPriceDto getLowestAndHighestPrice(@PathVariable String name) {
    return lowestHighestPriceQuery.execute(name);
  }
}
