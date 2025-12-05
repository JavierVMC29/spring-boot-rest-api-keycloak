package com.javiervmc.tutorials.companies_keycloak.core.exception;


public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
