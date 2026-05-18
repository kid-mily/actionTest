package com.kidmily.actiontest.controller;

import com.kidmily.actiontest.annotation.ApiErrorCodeExamples;
import com.kidmily.actiontest.dto.UserDto;
import com.kidmily.actiontest.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "1. User API", description = "사용자 관리 API")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Operation(summary = "사용자 목록 조회", description = "시스템에 등록된 전체 사용자 목록을 반환합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class)),
                    examples = @ExampleObject(value = """
                            [
                              {
                                "id": 1,
                                "username": "홍길동",
                                "email": "hong@example.com",
                                "status": "ACTIVE"
                              },
                              {
                                "id": 2,
                                "username": "김철수",
                                "email": "kim@example.com",
                                "status": "INACTIVE"
                              }
                            ]
                            """
                    )
            )
    )
    @ApiErrorCodeExamples(ErrorCode.SERVER_ERROR) // 🌟 500 에러 자동화
    @GetMapping
    public List<UserDto> getUsers() {
        return List.of(
                new UserDto(1L, "홍길동", "hong@example.com", "ACTIVE"),
                new UserDto(2L, "김철수", "kim@example.com", "INACTIVE")
        );
    }

    @Operation(summary = "새 사용자 생성", description = "새로운 사용자를 시스템에 등록합니다.")
    @ApiResponse(responseCode = "201", description = "생성 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "id": 3,
                              "username": "이영희",
                              "email": "lee@example.com",
                              "status": "ACTIVE"
                            }
                            """
                    )
            )
    )
    @ApiErrorCodeExamples({
            ErrorCode.INVALID_USER_INPUT, // 🌟 400 에러 자동화
            ErrorCode.DUPLICATE_EMAIL     // 🌟 409 에러 자동화
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return new UserDto(3L, userDto.username(), userDto.email(), "ACTIVE");
    }

    @Operation(summary = "사용자 상세 조회", description = "ID를 기반으로 특정 사용자의 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "id": 1,
                              "username": "홍길동",
                              "email": "hong@example.com",
                              "status": "ACTIVE"
                            }
                            """
                    )
            )
    )
    @ApiErrorCodeExamples(ErrorCode.USER_NOT_FOUND) // 🌟 404 에러 자동화
    @GetMapping("/{userId}")
    public UserDto viewUser(
            @Parameter(description = "조회할 사용자 ID", example = "1")
            @PathVariable("userId") Long userId) {
        return new UserDto(userId, "홍길동", "hong@example.com", "ACTIVE");
    }

    @Operation(summary = "사용자 정보 수정", description = "기존 사용자의 정보를 업데이트합니다.")
    @ApiResponse(responseCode = "200", description = "수정 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "id": 1,
                              "username": "홍길동(수정됨)",
                              "email": "hong_new@example.com",
                              "status": "ACTIVE"
                            }
                            """
                    )
            )
    )
    @ApiErrorCodeExamples({
            ErrorCode.INVALID_USER_INPUT, // 🌟 400 에러 자동화
            ErrorCode.USER_NOT_FOUND      // 🌟 404 에러 자동화
    })
    @PutMapping("/{userId}")
    public UserDto updateUser(
            @Parameter(description = "수정할 사용자 ID", example = "1") @PathVariable("userId") Long userId,
            @RequestBody UserDto userDto) {
        return new UserDto(userId, userDto.username(), userDto.email(), userDto.status());
    }

    @Operation(summary = "사용자 삭제", description = "시스템에서 특정 사용자를 삭제(또는 비활성화) 처리합니다.")
    @ApiResponse(responseCode = "204", description = "삭제 성공 (응답 본문 없음)")
    @ApiErrorCodeExamples(ErrorCode.USER_NOT_FOUND) // 🌟 404 에러 자동화
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void deleteUser(
            @Parameter(description = "삭제할 사용자 ID", example = "1") @PathVariable("userId") Long userId) {
        // 실제 구현 시 DB에서 사용자 삭제 로직을 수행합니다.
    }
}