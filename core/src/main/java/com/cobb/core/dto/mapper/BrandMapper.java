package com.cobb.core.dto.mapper;

import com.cobb.core.domain.entity.Brand;
import com.cobb.core.dto.BrandDto;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

  public BrandDto map(Brand brand) {
    return BrandDto.builder()
            .name(brand.getName())
            .build();
  }
}
