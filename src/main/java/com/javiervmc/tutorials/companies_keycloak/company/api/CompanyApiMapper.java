package com.javiervmc.tutorials.companies_keycloak.company.api;

import com.javiervmc.tutorials.companies_keycloak.company.api.dto.CreateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.company.api.dto.UpdateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.company.api.response.CompanyResponse;
import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;

import com.javiervmc.tutorials.companies_keycloak.core.api.PagedResponse;
import com.javiervmc.tutorials.companies_keycloak.core.domain.PagedResult;

import lombok.NoArgsConstructor;

import java.util.List;


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

    public static PagedResponse<CompanyResponse> mapPagedResultToResponse(PagedResult<Company> pagedResult) {
        List<CompanyResponse> content = pagedResult.getContent()
                .stream()
                .map(CompanyApiMapper::mapDomainToResponse)
                .toList();

        PagedResponse<CompanyResponse> response = new PagedResponse<>();
        response.setContent(content);
        response.setPageNo(pagedResult.getPageNumber());
        response.setPageSize(pagedResult.getPageSize());
        response.setTotalElements(pagedResult.getTotalElements());
        response.setTotalPages(pagedResult.getTotalPages());
        response.setLast(pagedResult.isLast());

        return response;
    }
}
