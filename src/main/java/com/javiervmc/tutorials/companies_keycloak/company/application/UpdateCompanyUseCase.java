package com.javiervmc.tutorials.companies_keycloak.company.application;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCompanyUseCase {
    private final CompanyRepository companyRepository;

    public Company execute(Company company, Long id) {
        return companyRepository.updateCompany(company, id);
    }
}
