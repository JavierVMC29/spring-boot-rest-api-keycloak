package com.javiervmc.tutorials.companies_keycloak.controller;

import com.javiervmc.tutorials.companies_keycloak.dto.CompanyDto;
import com.javiervmc.tutorials.companies_keycloak.dto.CreateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.dto.GetCompaniesResponse;
import com.javiervmc.tutorials.companies_keycloak.dto.UpdateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("")
    public ResponseEntity<GetCompaniesResponse> getCompanies(
            @RequestParam(
                    value = "pageNo",
                    defaultValue = "0",
                    required = false) int pageNo,
            @RequestParam(
                    value = "pageSize",
                    defaultValue = "10",
                    required = false) int pageSize
    ){
        return new ResponseEntity<GetCompaniesResponse>(
                companyService.getAllCompanies(pageNo, pageSize), HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<CompanyDto> companyDetail(
            @PathVariable Long id){
        return new ResponseEntity<CompanyDto>(
                companyService.getCompanyById(id), HttpStatus.OK
        );
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CompanyDto> createCompany(
            @RequestBody CreateCompanyDto dto){
        return new ResponseEntity<CompanyDto>(
                companyService.createCompany(dto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<CompanyDto> updateCompany(
            @RequestBody UpdateCompanyDto dto,
            @PathVariable("id") Long id
    ){
        CompanyDto response = companyService.updateCompany(dto, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CompanyDto> deleteCompany(
            @PathVariable("id") Long id
    ){
        CompanyDto response = companyService.deleteCompany(id);
        return new ResponseEntity<CompanyDto>(response, HttpStatus.OK);
    }
}