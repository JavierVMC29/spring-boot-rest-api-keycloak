package com.javiervmc.tutorials.companies_keycloak.mapper;

import com.javiervmc.tutorials.companies_keycloak.dto.CompanyDto;
import com.javiervmc.tutorials.companies_keycloak.dto.CreateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.entity.Company;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class CompanyMapper {
    public static CompanyDto mapToDto(Company company){
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(company.getId());
        companyDto.setName(company.getName());
        return companyDto;
    }

    public static Company mapCreateCompanyDtoToEntity(CreateCompanyDto dto) {
        Company company = new Company();
        company.setName(dto.getName());
        // BaseModel fields (createdAt/updatedAt) are auto-managed by JPA
        return company;
    }
}
