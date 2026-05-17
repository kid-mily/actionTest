package com.kidmily.actiontest.controller;

import com.kidmily.actiontest.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "2. Product API", description = "상품 관리 API")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Operation(summary = "상품 목록 조회", description = "등록된 모든 상품을 조회합니다.")
    @GetMapping
    public List<ProductDto> getProducts() {
        return List.of(new ProductDto(100L, "기계식 키보드", 150000));
    }

    @Operation(summary = "상품 등록", description = "새로운 상품을 시스템에 등록합니다.")
    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productDto;
    }
}