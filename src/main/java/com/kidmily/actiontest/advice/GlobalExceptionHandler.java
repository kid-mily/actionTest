package com.kidmily.actiontest.advice;

import com.kidmily.actiontest.dto.ErrorResponse;
import com.kidmily.actiontest.exception.CustomException;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        // SLF4J MDC에 이미 저장된 traceId가 있다면 가져오고, 없다면 새로 생성합니다.
        String traceId = MDC.get("traceId");
        if (traceId == null) {
            traceId = UUID.randomUUID().toString();
        }

        ErrorResponse response = new ErrorResponse(
                e.getErrorCode().getStatus().value(),
                e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                traceId // 🌟 traceId 추가
        );

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(response);
    }
}