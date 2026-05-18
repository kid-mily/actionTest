package com.kidmily.actiontest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공통 에러 응답 데이터")
public record ErrorResponse(
        @Schema(description = "HTTP 상태 코드", example = "404")
        int status,

        @Schema(description = "에러 분류 코드", example = "USER_001")
        String errorCode,

        @Schema(description = "에러 상세 메시지", example = "해당 사용자를 찾을 수 없습니다.")
        String message
) {}