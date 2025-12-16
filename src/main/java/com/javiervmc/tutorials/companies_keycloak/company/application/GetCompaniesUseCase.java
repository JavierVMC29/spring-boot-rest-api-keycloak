package com.javiervmc.tutorials.companies_keycloak.company.application;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyRepository;
import com.javiervmc.tutorials.companies_keycloak.core.domain.PagedResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetCompaniesUseCase {
    private final CompanyRepository companyRepository;

    public PagedResult<Company> execute(Integer pageNo, Integer pageSize) {
        log.info("Fetching companies list. Page: [{}], Size: [{}]", pageNo, pageSize);

        PagedResult<Company> result = companyRepository.getAllCompanies(pageNo, pageSize);

        log.info("Companies fetched successfully. Total elements: [{}]", result.getTotalElements());
        return result;
    }
}