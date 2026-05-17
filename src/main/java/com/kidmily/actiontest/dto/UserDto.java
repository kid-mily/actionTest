package com.kidmily.actiontest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 정보 응답 DTO")
public record UserDto(
        @Schema(description = "사용자 고유 ID", example = "1")
        Long id,

        @Schema(description = "사용자 이름", example = "홍길동")
        String username,

        @Schema(description = "이메일 주소", example = "user@example.com")
        String email
) {}