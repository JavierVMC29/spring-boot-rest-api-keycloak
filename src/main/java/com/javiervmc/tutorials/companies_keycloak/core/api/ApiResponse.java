package com.javiervmc.tutorials.companies_keycloak.core.api;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse<T> {
    private ResponseStatus status;
    private int statusCode;
    private String message;
    private LocalDateTime timestamp;
    private T data;
}

