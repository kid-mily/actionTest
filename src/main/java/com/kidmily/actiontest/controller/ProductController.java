package com.kidmily.actiontest.controller;

import com.kidmily.actiontest.annotation.ApiErrorCodeExample;
import com.kidmily.actiontest.dto.ProductDto;
import com.kidmily.actiontest.exception.GlobalErrorCode;
import com.kidmily.actiontest.exception.ProductErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "2. Product API", description = "상품 관리 API")
@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @Operation(summary = "상품 목록 조회", description = "등록된 모든 상품을 조회합니다.")
    @ApiErrorCodeExample(domain = GlobalErrorCode.class, value = "SERVER_ERROR")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = List.of(
                new ProductDto(100L, "기계식 키보드", 150000),
                new ProductDto(200L, "버티컬 마우스", 79000)
        );
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "상품 등록", description = "새로운 상품을 시스템에 등록합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiErrorCodeExample(domain = GlobalErrorCode.class, value = "INVALID_REQUEST")
    @ApiErrorCodeExample(domain = ProductErrorCode.class, value = "INVALID_PRICE")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = new ProductDto(101L, productDto.name(), productDto.price());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }
}