package com.javiervmc.tutorials.companies_keycloak.company.api;

import com.javiervmc.tutorials.companies_keycloak.company.api.response.CompanyResponse;
import com.javiervmc.tutorials.companies_keycloak.company.api.dto.CreateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.company.api.dto.UpdateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.company.application.*;
import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;

import com.javiervmc.tutorials.companies_keycloak.core.api.PagedResponse;
import com.javiervmc.tutorials.companies_keycloak.core.domain.PagedResult;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
    public ResponseEntity<PagedResponse<CompanyResponse>> getCompanies(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)
            @Min(value = 0, message = "pageNo must be >= 0")
            int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false)
            @Min(value = 1, message = "pageSize must be >= 1")
            @Max(value = 100, message = "pageSize cannot exceed 100")
            int pageSize
    ){
        log.info("Request received to fetch companies. Page: {}, Size: {}", pageNo, pageSize);

        PagedResult<Company> result = getCompaniesUseCase.execute(pageNo, pageSize);
        PagedResponse<CompanyResponse> response = CompanyApiMapper.mapPagedResultToResponse(result);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('view-companies')")
    @GetMapping("{id}")
    public ResponseEntity<CompanyResponse> companyDetail(
            @PathVariable
            @Min(value = 1, message = "id must be a positive number")
            Long id){

        log.info("Request received to get Company details for ID: {}", id);

        Company company = getCompanyByIdUseCase.execute(id);
        CompanyResponse companyResponse = CompanyApiMapper.mapDomainToResponse(company);

        return new ResponseEntity<>(companyResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('create-companies')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CompanyResponse> createCompany(
            @Valid @RequestBody CreateCompanyDto dto){

        log.info("Request received to create new Company: {}", dto.getName());

        Company company = CompanyApiMapper.mapCreateCompanyDtoToDomain(dto);
        Company createdCompany = createCompanyUseCase.execute(company);
        CompanyResponse companyResponse = CompanyApiMapper.mapDomainToResponse(createdCompany);

        return new ResponseEntity<>(companyResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('update-companies')")
    @PutMapping("{id}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @Valid @RequestBody UpdateCompanyDto dto,
            @PathVariable("id")
            @Min(value = 1, message = "id must be a positive number")
            Long id
    ){
        log.info("Request received to update Company ID: {}", id);

        Company company = CompanyApiMapper.mapUpdateCompanyDtoToDomain(dto);
        Company updatedCompany = updateCompanyUseCase.execute(company, id);
        CompanyResponse companyResponse = CompanyApiMapper.mapDomainToResponse(updatedCompany);
        return new ResponseEntity<>(companyResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('delete-companies')")
    @DeleteMapping("{id}")
    public ResponseEntity<CompanyResponse> deleteCompany(
            @PathVariable("id")
            @Min(value = 1, message = "id must be a positive number")
            Long id
    ){
        log.info("Request received to delete Company ID: {}", id);

        Company deletedCompany = deleteCompanyUseCase.execute(id);
        CompanyResponse companyResponse = CompanyApiMapper.mapDomainToResponse(deletedCompany);
        return new ResponseEntity<>(companyResponse, HttpStatus.OK);
    }
}