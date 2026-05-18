package com.kidmily.actiontest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주문 결제 정보 DTO")
public record OrderDto(
        @Schema(description = "주문 번호", example = "999", accessMode = Schema.AccessMode.READ_ONLY)
        Long orderId,

        @Schema(description = "주문자 고유 ID", example = "1")
        Long userId,

        @Schema(description = "주문 상품 ID", example = "100")
        Long productId,

        @Schema(description = "주문 수량", example = "2")
        int quantity
) {}