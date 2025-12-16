package com.javiervmc.tutorials.companies_keycloak.company.application;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateCompanyUseCase {
    private final CompanyRepository companyRepository;

    public Company execute(Company company, Long id) {
        log.info("Initiating update for company ID: [{}]. New Name: [{}]", id, company.getName());

        Company updatedCompany = companyRepository.updateCompany(company, id);

        log.info("Company with ID: [{}] updated successfully", id);
        return updatedCompany;
    }
}