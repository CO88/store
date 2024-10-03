package com.cobb.api.adapter.in.http;

import com.cobb.core.application.handler.command.CreateBrandHandler;
import com.cobb.core.application.handler.command.RemoveBrandHandler;
import com.cobb.core.application.handler.command.UpdateBrandHandler;
import com.cobb.core.application.handler.query.BrandLowestTotalQuery;
import com.cobb.core.application.handler.query.BrandQuery;
import com.cobb.core.dto.BrandDto;
import com.cobb.core.dto.LowestTotalDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BrandController {

  private final CreateBrandHandler createBrandHandler;
  private final RemoveBrandHandler removeBrandHandler;
  private final UpdateBrandHandler updateBrandHandler;

  private final BrandQuery brandQuery;
  private final BrandLowestTotalQuery brandLowestTotalQuery;

  @GetMapping("/brands")
  public List<BrandDto> gets() {
    return brandQuery.gets();
  }

  @PostMapping("/brands")
  public BrandDto create(@RequestBody BrandDto brandDto) {
    return createBrandHandler.execute(brandDto.name());
  }

  @Operation(
          summary = "단일 브랜드로 모든 카테고리 상품 리스트 및 총액 조회"
  )
  @GetMapping("/brands/{name}/lowest-price-product")
  public LowestTotalDto getLowestTotalPrice(@PathVariable String name) {
    return brandLowestTotalQuery.execute(name);
  }

  @PatchMapping("/brands/{id}")
  public BrandDto update(@PathVariable Long id, @RequestBody BrandDto brandDto) {
    return updateBrandHandler.execute(id, brandDto.name());
  }

  @DeleteMapping("/brands/{id}")
  public boolean delete(@PathVariable Long id) {
    removeBrandHandler.execute(id);
    return true;
  }
}
