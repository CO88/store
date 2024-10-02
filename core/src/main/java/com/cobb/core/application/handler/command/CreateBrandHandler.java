package com.cobb.core.application.handler.command;

import com.cobb.base.error.exception.BadRequestException;
import com.cobb.core.domain.entity.Brand;
import com.cobb.core.domain.port.BrandPort;
import com.cobb.core.dto.BrandDto;
import com.cobb.core.dto.mapper.BrandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateBrandHandler {

  private final BrandPort brandPort;
  private final BrandMapper brandMapper;

  public BrandDto execute(String name) {
    if (brandPort.existsByName(name)) {
      throw new BadRequestException("Brand with name " + name + " already exists");
    }

    Brand newBrand = Brand.create(name);
    return brandMapper.map(
            brandPort.save(newBrand)
    );
  }
}
