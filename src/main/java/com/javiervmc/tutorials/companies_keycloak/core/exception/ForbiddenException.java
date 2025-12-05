package com.javiervmc.tutorials.companies_keycloak.core.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
