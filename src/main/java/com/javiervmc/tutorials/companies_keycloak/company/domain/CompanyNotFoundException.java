package com.javiervmc.tutorials.companies_keycloak.company.domain;

import com.javiervmc.tutorials.companies_keycloak.core.exception.NotFoundException;

public class CompanyNotFoundException extends NotFoundException {
    public CompanyNotFoundException(Long id) {
        super("Company with id " + id + " not found");
    }
}
