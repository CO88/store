package com.cobb.api.adapter.in.http;

import com.cobb.core.application.handler.query.CategoryLowestHighestPriceQuery;
import com.cobb.core.application.handler.query.CategoryLowestPriceQuey;
import com.cobb.core.dto.CategoryLowestHighestPriceDto;
import com.cobb.core.dto.CategoryLowestPriceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryLowestPriceQuey lowestPriceQuey;
  private final CategoryLowestHighestPriceQuery lowestHighestPriceQuery;

  @GetMapping("/categories/lowest-price")
  public CategoryLowestPriceDto get() {
    return lowestPriceQuey.execute();
  }

  @GetMapping("/categories/{name}/price")
  public CategoryLowestHighestPriceDto getLowestAndHighestPrice(@PathVariable String name) {
    return lowestHighestPriceQuery.execute(name);
  }
}
