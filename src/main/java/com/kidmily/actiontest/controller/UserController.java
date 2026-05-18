package com.kidmily.actiontest.controller;


import com.kidmily.actiontest.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "1. User API", description = "사용자 관리 API")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Operation(summary = "사용자 목록 조회", description = "전체 사용자 목록을 반환합니다.")
    @GetMapping
    public List<UserDto> getUsers() {
        return List.of(new UserDto(1L, "홍길동", "user@example.com"));
    }

    @Operation(summary = "새 사용자 생성", description = "새로운 사용자를 등록합니다.")
    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userDto;
    }

    @Operation(summary = "사용자 상세 조회", description = "사용자를 조회합니다.")
    @GetMapping("/{userId}")
    public UserDto viewUser(@RequestParam String userId) {
        return new UserDto(1L, "홍길동", "user@example.com");

    }
}