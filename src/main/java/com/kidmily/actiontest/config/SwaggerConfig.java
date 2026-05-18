package com.kidmily.actiontest.config;

import com.kidmily.actiontest.annotation.ApiErrorCodeExample;
import com.kidmily.actiontest.dto.ErrorResponse;
import com.kidmily.actiontest.exception.BaseErrorCode;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class SwaggerConfig {

    @Bean
    public OperationCustomizer customErrorCodeCustomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            ApiErrorCodeExample[] annotations = handlerMethod.getMethod().getAnnotationsByType(ApiErrorCodeExample.class);
            if (annotations.length == 0) {
                return operation;
            }

            ApiResponses responses = operation.getResponses();

            for (ApiErrorCodeExample apiExample : annotations) {
                Class<? extends BaseErrorCode> domainClass = apiExample.domain();

                for (String codeName : apiExample.value()) {
                    // 🌟 컴파일 에러 해결 포인트: 제네릭 캐스팅 대신 getEnumConstants() 사용
                    BaseErrorCode errorCode = getErrorCodeInstance(domainClass, codeName);

                    if (errorCode == null) {
                        continue; // 일치하는 Enum이 없으면 건너뜀
                    }

                    String statusCode = String.valueOf(errorCode.getStatus().value());

                    ErrorResponse errorResponseExample = new ErrorResponse(
                            errorCode.getStatus().value(),
                            errorCode.getCode(),
                            errorCode.getMessage()
                    );

                    Example example = new Example();
                    example.value(errorResponseExample);
                    example.description(errorCode.getMessage());

                    ApiResponse apiResponse = responses.containsKey(statusCode)
                            ? responses.get(statusCode)
                            : new ApiResponse().description(errorCode.getStatus().getReasonPhrase());

                    Content content = apiResponse.getContent();
                    if (content == null) content = new Content();

                    MediaType mediaType = content.get("application/json");
                    if (mediaType == null) mediaType = new MediaType();

                    String exampleKey = domainClass.getSimpleName().toUpperCase() + "_" + codeName;
                    mediaType.addExamples(exampleKey, example);

                    content.addMediaType("application/json", mediaType);
                    apiResponse.setContent(content);

                    responses.addApiResponse(statusCode, apiResponse);
                }
            }

            return operation;
        };
    }

    // 🌟 안전하게 Enum 인스턴스를 추출하는 헬퍼 메서드 추가
    private BaseErrorCode getErrorCodeInstance(Class<? extends BaseErrorCode> domainClass, String codeName) {
        if (domainClass.isEnum()) {
            for (BaseErrorCode enumConstant : domainClass.getEnumConstants()) {
                if (((Enum<?>) enumConstant).name().equals(codeName)) {
                    return enumConstant;
                }
            }
        }
        return null;
    }
}