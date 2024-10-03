package com.cobb.core.application.handler.query;

import com.cobb.core.domain.entity.Brand;
import com.cobb.core.domain.port.BrandPort;
import com.cobb.core.dto.BrandDto;
import com.cobb.core.dto.mapper.BrandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandQuery {

  private final BrandPort brandPort;
  private final BrandMapper brandMapper;

  public List<BrandDto> gets() {
    List<Brand> brands = brandPort.findAll();

    return brands.stream().map(brandMapper::map).toList();
  }
}
