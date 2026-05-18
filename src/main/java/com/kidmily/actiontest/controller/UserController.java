package com.kidmily.actiontest.controller;

import com.kidmily.actiontest.annotation.ApiErrorCodeExample;
import com.kidmily.actiontest.dto.UserDto;
import com.kidmily.actiontest.exception.GlobalErrorCode;
import com.kidmily.actiontest.exception.UserErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "1. User API", description = "사용자 관리 API")
@RestController
// 🌟 produces 속성을 추가하여 이 컨트롤러의 모든 API가 기본적으로 JSON을 반환하도록 설정 (Swagger의 */* 문제 해결)
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Operation(summary = "사용자 목록 조회", description = "시스템에 등록된 전체 사용자 목록을 반환합니다.")
    // 🌟 성공 응답 어노테이션 제거! (Springdoc이 ResponseEntity<List<UserDto>>를 보고 200 OK 자동 생성)
    @ApiErrorCodeExample(domain = GlobalErrorCode.class, value = "SERVER_ERROR")
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = List.of(
                new UserDto(1L, "홍길동", "hong@example.com", "ACTIVE"),
                new UserDto(2L, "김철수", "kim@example.com", "INACTIVE")
        );
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "새 사용자 생성", description = "새로운 사용자를 시스템에 등록합니다.")
    // 🌟 201 Created 자동 생성을 위해 @ResponseStatus 추가
    @ResponseStatus(HttpStatus.CREATED)
    @ApiErrorCodeExample(domain = GlobalErrorCode.class, value = "INVALID_REQUEST")
    @ApiErrorCodeExample(domain = UserErrorCode.class, value = {"INVALID_USER_INPUT", "DUPLICATE_EMAIL"})
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = new UserDto(3L, userDto.username(), userDto.email(), "ACTIVE");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "사용자 상세 조회", description = "ID를 기반으로 특정 사용자의 상세 정보를 조회합니다.")
    @ApiErrorCodeExample(domain = UserErrorCode.class, value = "USER_NOT_FOUND")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> viewUser(
            @Parameter(description = "조회할 사용자 ID", example = "1")
            @PathVariable("userId") Long userId) {

        UserDto user = new UserDto(userId, "홍길동", "hong@example.com", "ACTIVE");
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "사용자 정보 수정", description = "기존 사용자의 정보를 업데이트합니다.")
    @ApiErrorCodeExample(domain = UserErrorCode.class, value = {"INVALID_USER_INPUT", "USER_NOT_FOUND"})
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @Parameter(description = "수정할 사용자 ID", example = "1") @PathVariable("userId") Long userId,
            @RequestBody UserDto userDto) {

        UserDto updatedUser = new UserDto(userId, userDto.username(), userDto.email(), userDto.status());
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "사용자 삭제", description = "시스템에서 특정 사용자를 삭제 처리합니다.")
    // 🌟 204 No Content 자동 생성을 위해 @ResponseStatus 추가
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiErrorCodeExample(domain = UserErrorCode.class, value = "USER_NOT_FOUND")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "삭제할 사용자 ID", example = "1") @PathVariable("userId") Long userId) {

        return ResponseEntity.noContent().build();
    }
}