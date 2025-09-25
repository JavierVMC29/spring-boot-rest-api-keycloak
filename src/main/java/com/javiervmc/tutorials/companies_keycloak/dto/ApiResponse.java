package com.javiervmc.tutorials.companies_keycloak.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse<T> {
    private ResponseStatus status;
    private T data;
    private String message;
    private LocalDateTime timestamp;
}

