package com.kidmily.actiontest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 정보 DTO")
public record ProductDto(
        @Schema(description = "상품 ID", example = "100")
        Long productId,

        @Schema(description = "상품명", example = "기계식 키보드")
        String name,

        @Schema(description = "가격", example = "150000")
        int price
) {}