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

    public Company execute(Company companyUpdateData, Long id) {
        log.info("Initiating update for company ID: [{}].", id);

        // 1. Fetch existing company (The "Real" state)
        Company existingCompany = companyRepository.getById(id);

        // 2. Modify state in memory (Business Logic)
        existingCompany.setName(companyUpdateData.getName());

        // 3. Save the modified object
        Company updatedCompany = companyRepository.save(existingCompany);

        log.info("Company with ID: [{}] updated successfully", id);
        return updatedCompany;
    }
}