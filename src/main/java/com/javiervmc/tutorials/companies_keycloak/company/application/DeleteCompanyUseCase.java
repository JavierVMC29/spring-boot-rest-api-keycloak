package com.javiervmc.tutorials.companies_keycloak.company.application;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCompanyUseCase {
    private final CompanyRepository companyRepository;

    public Company execute(Long id) {
        return companyRepository.deleteCompany(id);
    }
}
