package com.javiervmc.tutorials.companies_keycloak.exception;


public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
