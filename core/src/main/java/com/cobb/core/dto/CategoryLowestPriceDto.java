package com.cobb.core.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryLowestPriceDto(
        List<ProductDto> productDtos,
        int total
) {
}
