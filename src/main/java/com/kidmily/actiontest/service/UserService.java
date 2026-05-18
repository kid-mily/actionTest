package com.kidmily.actiontest.service;

import com.kidmily.actiontest.dto.UserDto;
import com.kidmily.actiontest.exception.CustomException;
import com.kidmily.actiontest.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserDto getUser(Long userId) {
        // DB에서 못 찾았다고 가정
        throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }
}