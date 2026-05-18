package com.kidmily.actiontest.annotation;

import com.kidmily.actiontest.exception.ErrorCode;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorCodeExamples {
    // API에서 발생할 수 있는 Enum 에러 코드들을 배열로 받습니다.
    ErrorCode[] value();
}