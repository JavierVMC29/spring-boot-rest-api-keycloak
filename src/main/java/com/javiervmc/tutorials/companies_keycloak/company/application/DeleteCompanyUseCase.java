package com.javiervmc.tutorials.companies_keycloak.company.application;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteCompanyUseCase {
    private final CompanyRepository companyRepository;

    public Company execute(Long id) {
        log.info("Initiating deletion of company with ID: [{}]", id);

        Company companyToDelete = companyRepository.getById(id);

        companyRepository.delete(id);

        log.info("Company with ID: [{}] deleted successfully", id);
        return companyToDelete;
    }
}