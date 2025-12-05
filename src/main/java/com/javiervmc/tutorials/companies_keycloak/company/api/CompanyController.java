package com.javiervmc.tutorials.companies_keycloak.company.api;

import com.javiervmc.tutorials.companies_keycloak.company.api.response.CompanyResponse;
import com.javiervmc.tutorials.companies_keycloak.company.api.dto.CreateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.company.api.response.GetCompaniesResponse;
import com.javiervmc.tutorials.companies_keycloak.company.api.dto.UpdateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.company.application.*;
import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final GetCompaniesUseCase getCompaniesUseCase;
    private final GetCompanyByIdUseCase getCompanyByIdUseCase;
    private final CreateCompanyUseCase createCompanyUseCase;
    private final UpdateCompanyUseCase updateCompanyUseCase;
    private final DeleteCompanyUseCase deleteCompanyUseCase;

    @Autowired
    public CompanyController(
            GetCompaniesUseCase getCompaniesUseCase,
            GetCompanyByIdUseCase getCompanyByIdUseCase,
            CreateCompanyUseCase createCompanyUseCase,
            UpdateCompanyUseCase updateCompanyUseCase,
            DeleteCompanyUseCase deleteCompanyUseCase
    ) {
        this.getCompaniesUseCase = getCompaniesUseCase;
        this.getCompanyByIdUseCase = getCompanyByIdUseCase;
        this.createCompanyUseCase = createCompanyUseCase;
        this.updateCompanyUseCase = updateCompanyUseCase;
        this.deleteCompanyUseCase = deleteCompanyUseCase;
    }

    @PreAuthorize("hasRole('view-companies')")
    @GetMapping("")
    public ResponseEntity<GetCompaniesResponse> getCompanies(
            @RequestParam(
                    value = "pageNo",
                    defaultValue = "0",
                    required = false)
            @Min(value = 0, message = "pageNo must be >= 0")
            int pageNo,
            @RequestParam(
                    value = "pageSize",
                    defaultValue = "10",
                    required = false)
            @Min(value = 1, message = "pageSize must be >= 1")
            @Max(value = 100, message = "pageSize cannot exceed 100")
            int pageSize
    ){
        Page<Company> companies = getCompaniesUseCase.execute(pageNo, pageSize);

        List<CompanyResponse> content = companies.getContent()
                .stream()
                .map(CompanyApiMapper::mapDomainToResponse)
                .toList();

        GetCompaniesResponse companiesResponse = new GetCompaniesResponse();
        companiesResponse.setContent(content);
        companiesResponse.setPageNo(companies.getNumber());
        companiesResponse.setPageSize(companies.getSize());
        companiesResponse.setTotalElements(companies.getTotalElements());
        companiesResponse.setTotalPages(companies.getTotalPages());
        companiesResponse.setLast(companies.isLast());

        return new ResponseEntity<GetCompaniesResponse>(companiesResponse, HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('view-companies')")
    @GetMapping("{id}")
    public ResponseEntity<CompanyResponse> companyDetail(
            @PathVariable
            @Min(value = 1, message = "id must be a positive number")
            Long id){
        Company company = getCompanyByIdUseCase.execute(id);
        CompanyResponse companyResponse = CompanyApiMapper.mapDomainToResponse(company);

        return new ResponseEntity<CompanyResponse>(companyResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('create-companies')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CompanyResponse> createCompany(
            @Valid @RequestBody CreateCompanyDto dto){
        Company company = CompanyApiMapper.mapCreateCompanyDtoToDomain(dto);
        Company createdCompany = createCompanyUseCase.execute(company);
        CompanyResponse companyResponse = CompanyApiMapper.mapDomainToResponse(createdCompany);

        return new ResponseEntity<CompanyResponse>(companyResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('update-companies')")
    @PutMapping("{id}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @Valid @RequestBody UpdateCompanyDto dto,
            @PathVariable("id")
            @Min(value = 1, message = "id must be a positive number")
            Long id
    ){
        Company company = CompanyApiMapper.mapUpdateCompanyDtoToDomain(dto);
        Company updatedCompany = updateCompanyUseCase.execute(company, id);
        CompanyResponse companyResponse = CompanyApiMapper.mapDomainToResponse(updatedCompany);
        return new ResponseEntity<CompanyResponse>(companyResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('delete-companies')")
    @DeleteMapping("{id}")
    public ResponseEntity<CompanyResponse> deleteCompany(
            @PathVariable("id")
            @Min(value = 1, message = "id must be a positive number")
            Long id
    ){
        Company deletedCompany = deleteCompanyUseCase.execute(id);
        CompanyResponse companyResponse = CompanyApiMapper.mapDomainToResponse(deletedCompany);
        return new ResponseEntity<CompanyResponse>(companyResponse, HttpStatus.OK);
    }
}