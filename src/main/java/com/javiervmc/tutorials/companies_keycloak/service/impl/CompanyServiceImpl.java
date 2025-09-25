package com.javiervmc.tutorials.companies_keycloak.service.impl;

import com.javiervmc.tutorials.companies_keycloak.dto.CompanyDto;
import com.javiervmc.tutorials.companies_keycloak.dto.CreateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.dto.GetCompaniesResponse;
import com.javiervmc.tutorials.companies_keycloak.dto.UpdateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.entity.Company;
import com.javiervmc.tutorials.companies_keycloak.exception.company.CompanyNotFoundException;
import com.javiervmc.tutorials.companies_keycloak.mapper.CompanyMapper;
import com.javiervmc.tutorials.companies_keycloak.repository.CompanyRepository;
import com.javiervmc.tutorials.companies_keycloak.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyDto createCompany(CreateCompanyDto dto) {
        Company company = CompanyMapper.mapCreateCompanyDtoToEntity(dto);
        Company newCompany = companyRepository.save(company);
        return CompanyMapper.mapToDto(newCompany);
    }

    @Override
    public GetCompaniesResponse getAllCompanies(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Company> companies = companyRepository.findAll(pageable);
        List<Company> listOfCompanies = companies.getContent();
        List<CompanyDto> content = listOfCompanies.stream().map(CompanyMapper::mapToDto).collect(Collectors.toList());

        GetCompaniesResponse companiesResponse = new GetCompaniesResponse();
        companiesResponse.setContent(content);
        companiesResponse.setPageNo(companies.getNumber());
        companiesResponse.setPageSize(companies.getSize());
        companiesResponse.setTotalElements(companies.getTotalElements());
        companiesResponse.setTotalPages(companies.getTotalPages());
        companiesResponse.setLast(companies.isLast());

        return companiesResponse;
    }

    @Override
    public CompanyDto getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
        return CompanyMapper.mapToDto(company);
    }

    @Override
    public CompanyDto updateCompany(UpdateCompanyDto dto, Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));

        company.setName(dto.getName());

        Company updatedCompany = companyRepository.save(company);

        return CompanyMapper.mapToDto(updatedCompany);
    }

    @Override
    public CompanyDto deleteCompany(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
        companyRepository.delete(company);
        return CompanyMapper.mapToDto(company);
    }
}
