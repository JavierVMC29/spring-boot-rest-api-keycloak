package com.javiervmc.tutorials.companies_keycloak.company.api;

import com.javiervmc.tutorials.companies_keycloak.company.api.dto.CreateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.company.api.dto.UpdateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.company.api.response.CompanyResponse;
import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class CompanyApiMapper {
    public static CompanyResponse mapDomainToResponse(Company company){
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        return companyResponse;
    }

    public static Company mapCreateCompanyDtoToDomain(CreateCompanyDto dto){
        Company company = new Company();
        company.setName(dto.getName());
        return company;
    }

    public static Company mapUpdateCompanyDtoToDomain(UpdateCompanyDto dto){
        Company company = new Company();
        company.setName(dto.getName());
        return company;
    }
}
