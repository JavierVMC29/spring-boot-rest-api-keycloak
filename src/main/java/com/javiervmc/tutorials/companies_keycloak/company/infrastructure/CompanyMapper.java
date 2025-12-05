package com.javiervmc.tutorials.companies_keycloak.company.infrastructure;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class CompanyMapper {
    public static Company mapEntityToDomain(CompanyEntity companyEntity){
        Company company = new Company();
        company.setId(companyEntity.getId());
        company.setName(companyEntity.getName());
        return company;
    }

    public static CompanyEntity mapDomainToEntity(Company company){
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(company.getId());
        companyEntity.setName(company.getName());
        return companyEntity;
    }
}
