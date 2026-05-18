package com.kidmily.actiontest.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements BaseErrorCode {
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_001", "존재하지 않는 상품입니다."),
    INVALID_PRICE(HttpStatus.BAD_REQUEST, "PRODUCT_002", "상품 가격은 0원 이상이어야 합니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT_003", "선택하신 상품의 재고가 부족합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}