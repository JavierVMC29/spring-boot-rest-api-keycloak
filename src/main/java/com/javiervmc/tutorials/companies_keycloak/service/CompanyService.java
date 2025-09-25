package com.javiervmc.tutorials.companies_keycloak.service;

import com.javiervmc.tutorials.companies_keycloak.dto.CompanyDto;
import com.javiervmc.tutorials.companies_keycloak.dto.CreateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.dto.GetCompaniesResponse;
import com.javiervmc.tutorials.companies_keycloak.dto.UpdateCompanyDto;

public interface CompanyService {
    CompanyDto createCompany(CreateCompanyDto dto);
    GetCompaniesResponse getAllCompanies(int pageNo, int pageSize);
    CompanyDto getCompanyById(Long id);
    CompanyDto updateCompany(UpdateCompanyDto companyDto, Long id);
    CompanyDto deleteCompany(Long id);
}
