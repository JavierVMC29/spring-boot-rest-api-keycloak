package com.javiervmc.tutorials.companies_keycloak.company.domain;

import com.javiervmc.tutorials.companies_keycloak.core.domain.PagedResult;

public interface CompanyRepository {
    // Generic Save: Handles both Create (Insert) and Update
    Company save(Company company);

    // Reads
    Company getById(Long id);
    PagedResult<Company> getAll(int pageNo, int pageSize);

    // Delete
    void delete(Long id);
}