package com.javiervmc.tutorials.companies_keycloak.company.domain;

import com.javiervmc.tutorials.companies_keycloak.core.domain.PagedResult;

public interface CompanyRepository {
    Company createCompany(Company dto);
    PagedResult<Company> getAllCompanies(int pageNo, int pageSize);
    Company getCompanyById(Long id);
    Company updateCompany(Company dto, Long id);
    Company deleteCompany(Long id);
}