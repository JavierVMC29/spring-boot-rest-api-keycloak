package com.javiervmc.tutorials.companies_keycloak.company.domain;

import org.springframework.data.domain.Page;


public interface CompanyRepository {
    Company createCompany(Company dto);
    Page<Company> getAllCompanies(int pageNo, int pageSize);
    Company getCompanyById(Long id);
    Company updateCompany(Company dto, Long id);
    Company deleteCompany(Long id);
}

