package com.javiervmc.tutorials.companies_keycloak.controller;

import com.javiervmc.tutorials.companies_keycloak.dto.CompanyDto;
import com.javiervmc.tutorials.companies_keycloak.dto.CreateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.dto.GetCompaniesResponse;
import com.javiervmc.tutorials.companies_keycloak.dto.UpdateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.service.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
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
        return new ResponseEntity<GetCompaniesResponse>(
                companyService.getAllCompanies(pageNo, pageSize), HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('view-companies')")
    @GetMapping("{id}")
    public ResponseEntity<CompanyDto> companyDetail(
            @PathVariable
            @Min(value = 1, message = "id must be a positive number")
            Long id){
        return new ResponseEntity<CompanyDto>(
                companyService.getCompanyById(id), HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('create-companies')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CompanyDto> createCompany(
            @Valid @RequestBody CreateCompanyDto dto){
        return new ResponseEntity<CompanyDto>(
                companyService.createCompany(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('update-companies')")
    @PutMapping("{id}")
    public ResponseEntity<CompanyDto> updateCompany(
            @Valid @RequestBody UpdateCompanyDto dto,
            @PathVariable("id")
            @Min(value = 1, message = "id must be a positive number")
            Long id
    ){
        CompanyDto response = companyService.updateCompany(dto, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('delete-companies')")
    @DeleteMapping("{id}")
    public ResponseEntity<CompanyDto> deleteCompany(
            @PathVariable("id")
            @Min(value = 1, message = "id must be a positive number")
            Long id
    ){
        CompanyDto response = companyService.deleteCompany(id);
        return new ResponseEntity<CompanyDto>(response, HttpStatus.OK);
    }
}