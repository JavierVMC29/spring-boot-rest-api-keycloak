package com.javiervmc.tutorials.companies_keycloak.company.application;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateCompanyUseCase {
    private final CompanyRepository companyRepository;

    public Company execute(Company company) {
        log.info("Initiating creation of company with name: [{}]", company.getName());

        Company createdCompany = companyRepository.save(company);

        log.info("Company created successfully with ID: [{}]", createdCompany.getId());
        return createdCompany;
    }
}
