package com.cobb.core.application.handler.command;

import com.cobb.base.error.exception.NotFoundException;
import com.cobb.core.domain.entity.Brand;
import com.cobb.core.domain.port.BrandPort;
import com.cobb.core.dto.BrandDto;
import com.cobb.core.dto.mapper.BrandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateBrandHandler {

  private final BrandPort brandPort;
  private final BrandMapper brandMapper;

  public BrandDto execute(Long id, String name) {
    Brand brand = brandPort.findById(id).orElseThrow(NotFoundException::new);

    brand.setName(name);
    return brandMapper.map(brandPort.save(brand));
  }
}
