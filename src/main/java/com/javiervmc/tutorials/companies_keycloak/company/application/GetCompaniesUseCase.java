package com.javiervmc.tutorials.companies_keycloak.company.application;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCompaniesUseCase {
    private final CompanyRepository companyRepository;

    public Page<Company> execute(Integer pageNo, Integer pageSize) {
        return companyRepository.getAllCompanies(pageNo, pageSize);
    }
}
