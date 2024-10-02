package com.cobb.core.dto;

import lombok.Builder;

@Builder
public record ProductDto(
        Long id,
        String category,
        String brand,
        Integer price
) {
}
