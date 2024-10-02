package com.cobb.core.dto;

import lombok.Builder;

@Builder
public record CategoryLowestHighestPriceDto(
        String categoryName,
        ProductDto lowestProduct,
        ProductDto highestProduct
) {
}
