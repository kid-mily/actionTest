package com.kidmily.actiontest.controller;

import com.kidmily.actiontest.annotation.ApiErrorCodeExample;
import com.kidmily.actiontest.dto.OrderDto;
import com.kidmily.actiontest.exception.GlobalErrorCode;
import com.kidmily.actiontest.exception.OrderErrorCode;
import com.kidmily.actiontest.exception.ProductErrorCode;
import com.kidmily.actiontest.exception.UserErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "3. Order API", description = "주문 결제 API")
@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Operation(summary = "주문 내역 조회", description = "전체 주문 내역을 반환합니다.")
    @ApiErrorCodeExample(domain = GlobalErrorCode.class, value = "SERVER_ERROR")
    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orders = List.of(
                new OrderDto(999L, 1L, 100L, 2),
                new OrderDto(1000L, 1L, 200L, 1)
        );
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "주문 생성", description = "새로운 주문을 접수하고 결제를 진행합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    // 다양한 도메인의 예외 상황을 조립하듯 직관적으로 명시 가능합니다.
    @ApiErrorCodeExample(domain = UserErrorCode.class, value = "USER_NOT_FOUND")
    @ApiErrorCodeExample(domain = ProductErrorCode.class, value = {"PRODUCT_NOT_FOUND", "OUT_OF_STOCK"})
    @ApiErrorCodeExample(domain = OrderErrorCode.class, value = {"INVALID_QUANTITY", "PAYMENT_FAILED"})
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto createdOrder = new OrderDto(999L, orderDto.userId(), orderDto.productId(), orderDto.quantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
}