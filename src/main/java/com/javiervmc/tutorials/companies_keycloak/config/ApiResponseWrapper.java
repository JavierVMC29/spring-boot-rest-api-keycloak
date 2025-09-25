package com.javiervmc.tutorials.companies_keycloak.config;

import com.javiervmc.tutorials.companies_keycloak.dto.ApiResponse;
import com.javiervmc.tutorials.companies_keycloak.dto.ResponseStatus;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiResponseWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        // Wrap all responses except ApiResponse itself
        return !returnType.getParameterType().equals(ApiResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        // If the body is null (e.g., DELETE 204), return empty data
        if (body == null) {
            return ApiResponse.builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(null)
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        return ApiResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .data(body)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
