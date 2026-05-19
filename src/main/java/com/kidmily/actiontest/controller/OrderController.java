package com.kidmily.actiontest.controller;

import com.kidmily.actiontest.annotation.ApiErrorCodeExample;
import com.kidmily.actiontest.dto.FileUploadResponseDto;
import com.kidmily.actiontest.dto.OrderDto;
import com.kidmily.actiontest.exception.GlobalErrorCode;
import com.kidmily.actiontest.exception.OrderErrorCode;
import com.kidmily.actiontest.exception.ProductErrorCode;
import com.kidmily.actiontest.exception.UserErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // 🌟 MultipartFile 임포트

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
    @ApiErrorCodeExample(domain = UserErrorCode.class, value = "USER_NOT_FOUND")
    @ApiErrorCodeExample(domain = ProductErrorCode.class, value = {"PRODUCT_NOT_FOUND", "OUT_OF_STOCK"})
    @ApiErrorCodeExample(domain = OrderErrorCode.class, value = {"INVALID_QUANTITY", "PAYMENT_FAILED"})
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto createdOrder = new OrderDto(999L, orderDto.userId(), orderDto.productId(), orderDto.quantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    // 🌟 1. 단일 이미지 업로드 엔드포인트
    @Operation(summary = "주문 영수증 이미지 업로드", description = "주문 내역 증빙을 위한 영수증 이미지를 업로드합니다. (최대 5MB)")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiErrorCodeExample(domain = GlobalErrorCode.class, value = {"INVALID_REQUEST", "SERVER_ERROR"})
    // consumes에 MULTIPART_FORM_DATA_VALUE를 명시해야 Swagger가 파일 업로드 UI를 그립니다.
    @PostMapping(value = "/{orderId}/receipt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponseDto> uploadReceiptImage(
            @Parameter(description = "주문 번호", example = "999") @PathVariable("orderId") Long orderId,
            @Parameter(description = "업로드할 이미지 파일 (jpg, png)") @RequestPart("imageFile") MultipartFile imageFile) {

        // 실제 환경에서는 S3 등에 업로드 처리
        String mockUrl = "https://cdn.example.com/receipts/" + imageFile.getOriginalFilename();
        FileUploadResponseDto response = new FileUploadResponseDto(mockUrl, imageFile.getSize());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 🌟 2. 동영상 업로드 + 텍스트 데이터 함께 받기 (Multipart)
    @Operation(summary = "주문 언박싱 동영상 업로드", description = "주문 상품의 언박싱 동영상과 설명을 함께 업로드합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiErrorCodeExample(domain = GlobalErrorCode.class, value = {"INVALID_REQUEST", "SERVER_ERROR"})
    @PostMapping(value = "/{orderId}/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponseDto> uploadUnboxingVideo(
            @Parameter(description = "주문 번호", example = "999") @PathVariable("orderId") Long orderId,
            @Parameter(description = "업로드할 동영상 파일 (mp4)") @RequestPart("videoFile") MultipartFile videoFile,
            @Parameter(description = "동영상 설명 텍스트", example = "키보드 타건 영상입니다.") @RequestPart("description") String description) {

        String mockUrl = "https://cdn.example.com/videos/" + videoFile.getOriginalFilename();
        FileUploadResponseDto response = new FileUploadResponseDto(mockUrl, videoFile.getSize());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}