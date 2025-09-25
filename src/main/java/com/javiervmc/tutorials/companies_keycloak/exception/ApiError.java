package com.javiervmc.tutorials.companies_keycloak.exception;

import com.javiervmc.tutorials.companies_keycloak.dto.ResponseStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiError {
    private ResponseStatus status;        // "error"
    private String message;       // human-readable message
    private int statusCode;       // HTTP status code
    private LocalDateTime timestamp;
}
