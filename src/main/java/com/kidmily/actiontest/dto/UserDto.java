package com.kidmily.actiontest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 정보 DTO")
public record UserDto(
        @Schema(
                description = "사용자 고유 ID (생성 요청 시에는 무시됨)",
                example = "1",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        Long id,

        @Schema(
                description = "사용자 이름",
                example = "홍길동",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 2,
                maxLength = 50
        )
        String username,

        @Schema(
                description = "이메일 주소",
                example = "user@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email,

        @Schema(
                description = "사용자 상태",
                example = "ACTIVE",
                allowableValues = {"ACTIVE", "INACTIVE", "BANNED"}
        )
        String status
) {}