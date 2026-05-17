package com.kidmily.actiontest.controller;

import com.kidmily.actiontest.dto.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "3. Order API", description = "주문 결제 API")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Operation(summary = "주문 내역 조회123", description = "전체 주문 내역을 반환합니다.")
    @GetMapping
    public List<OrderDto> getOrders() {
        return List.of(new OrderDto(999L, 1L, 100L, 2));
    }

    @Operation(summary = "주문 생성", description = "새로운 주문을 접수합니다.")
    @PostMapping
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        return orderDto;
    }
}