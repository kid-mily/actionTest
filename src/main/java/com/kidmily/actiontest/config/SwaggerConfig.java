package com.kidmily.actiontest.config;

import com.kidmily.actiontest.annotation.ApiErrorCodeExamples;
import com.kidmily.actiontest.dto.ErrorResponse; // 🌟 DTO 임포트 추가
import com.kidmily.actiontest.exception.ErrorCode;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OperationCustomizer customErrorCodeCustomizer() {
        return (Operation operation, org.springframework.web.method.HandlerMethod handlerMethod) -> {
            ApiErrorCodeExamples annotation = handlerMethod.getMethodAnnotation(ApiErrorCodeExamples.class);
            if (annotation == null) {
                return operation;
            }

            ApiResponses responses = operation.getResponses();

            for (ErrorCode errorCode : annotation.value()) {
                String statusCode = String.valueOf(errorCode.getStatus().value());

                // 🌟 핵심 변경 포인트: 문자열이 아닌 실제 객체(DTO)를 생성해서 넣습니다.
                ErrorResponse errorResponseExample = new ErrorResponse(
                        errorCode.getStatus().value(),
                        errorCode.getCode(),
                        errorCode.getMessage()
                );

                Example example = new Example();
                example.value(errorResponseExample); // 객체를 넘기면 Swagger가 완벽한 JSON으로 직렬화해줍니다!
                example.description(errorCode.getMessage());

                ApiResponse apiResponse = responses.containsKey(statusCode)
                        ? responses.get(statusCode)
                        : new ApiResponse().description(errorCode.getStatus().getReasonPhrase());

                Content content = apiResponse.getContent();
                if (content == null) content = new Content();

                MediaType mediaType = content.get("application/json");
                if (mediaType == null) mediaType = new MediaType();

                mediaType.addExamples(errorCode.name(), example);
                content.addMediaType("application/json", mediaType);
                apiResponse.setContent(content);

                responses.addApiResponse(statusCode, apiResponse);
            }

            return operation;
        };
    }
}