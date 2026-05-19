package com.kidmily.actiontest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "파일 업로드 응답 DTO")
public record FileUploadResponseDto(
        @Schema(description = "업로드된 파일의 접근 URL", example = "https://cdn.example.com/images/receipt_999.jpg")
        String fileUrl,

        @Schema(description = "파일 크기 (바이트)", example = "1048576")
        long fileSize
) {}