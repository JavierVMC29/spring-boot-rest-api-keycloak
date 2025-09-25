package com.javiervmc.tutorials.companies_keycloak.exception.company;

import com.javiervmc.tutorials.companies_keycloak.exception.NotFoundException;

public class CompanyNotFoundException extends NotFoundException {
    public CompanyNotFoundException(Long id) {
        super("Company with id " + id + " not found");
    }
}
