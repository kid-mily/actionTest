package com.kidmily.actiontest.advice;

import com.kidmily.actiontest.dto.ErrorResponse;
import com.kidmily.actiontest.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorResponse response = new ErrorResponse(
                e.getErrorCode().getStatus().value(),
                e.getErrorCode().getCode(),
                e.getErrorCode().getMessage()
        );

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(response);
    }
}