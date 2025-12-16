package com.javiervmc.tutorials.companies_keycloak.company.application;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetCompanyByIdUseCase {
    private final CompanyRepository companyRepository;

    public Company execute(Long id) {
        log.info("Fetching company details for ID: [{}]", id);

        Company company = companyRepository.getCompanyById(id);

        log.info("Company with ID: [{}] found", id);
        return company;
    }
}