package com.cobb.core.application.handler.query;

import com.cobb.base.error.exception.NotFoundException;
import com.cobb.core.domain.entity.LowestTotal;
import com.cobb.core.domain.port.LowestTotalPort;
import com.cobb.core.dto.LowestTotalDto;
import com.cobb.core.dto.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandLowestTotalQuery {

  private final LowestTotalPort lowestTotalPort;

  private final ProductMapper productMapper;

  public LowestTotalDto execute(String brandName) {
    LowestTotal lowestTotal = lowestTotalPort
            .findByBrand_Name(brandName)
            .orElseThrow(NotFoundException::new);

    return LowestTotalDto.builder()
            .brandName(lowestTotal.getBrand().getName())
            .products(lowestTotal.getProductLowestTotals().stream()
                    .map(productLowestTotal -> productMapper.map(productLowestTotal.getProduct()))
                    .toList())
            .total(lowestTotal.getTotal())
            .build();

  }
}
