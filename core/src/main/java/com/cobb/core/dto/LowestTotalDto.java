package com.cobb.core.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record LowestTotalDto(
        String brandName,
        List<ProductDto> products,
        int total
) {
}
