package com.cobb.api.adapter.in.http;

import com.cobb.core.application.handler.command.CreateBrandHandler;
import com.cobb.core.application.handler.command.RemoveBrandHandler;
import com.cobb.core.application.handler.command.UpdateBrandHandler;
import com.cobb.core.application.handler.query.BrandLowestTotalQuery;
import com.cobb.core.dto.BrandDto;
import com.cobb.core.dto.LowestTotalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BrandController {

  private final CreateBrandHandler createBrandHandler;
  private final RemoveBrandHandler removeBrandHandler;
  private final UpdateBrandHandler updateBrandHandler;

  private final BrandLowestTotalQuery brandLowestTotalQuery;

  @PostMapping("/brand")
  public BrandDto create(@RequestBody BrandDto brandDto) {
    return createBrandHandler.execute(brandDto.name());
  }

  @GetMapping("/brand/{name}/lowest-price")
  public LowestTotalDto getLowestTotalPrice(@PathVariable String name) {
    return brandLowestTotalQuery.execute(name);
  }

  @PatchMapping("/brand/{id}")
  public BrandDto update(@PathVariable Long id, @RequestBody BrandDto brandDto) {
    return updateBrandHandler.execute(id, brandDto.name());
  }

  @DeleteMapping("/brand/{id}")
  public boolean delete(@PathVariable Long id) {
    removeBrandHandler.execute(id);
    return true;
  }
}
