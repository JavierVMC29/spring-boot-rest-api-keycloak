package com.javiervmc.tutorials.companies_keycloak.exception;


public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
