package com.kidmily.actiontest.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_001", "주문 내역을 찾을 수 없습니다."),
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "ORDER_002", "최소 주문 수량은 1개 이상입니다."),
    PAYMENT_FAILED(HttpStatus.PAYMENT_REQUIRED, "ORDER_003", "결제 처리에 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}