package com.javiervmc.tutorials.companies_keycloak.company.infrastructure;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyNotFoundException;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyRepositoryImpl implements CompanyRepository {
    private final CompanyJpaRepository companyJpaRepository;

    @Autowired
    public CompanyRepositoryImpl(CompanyJpaRepository companyJpaRepository) {
        this.companyJpaRepository = companyJpaRepository;
    }

    @Override
    public Company createCompany(Company company) {
        CompanyEntity companyEntity = CompanyMapper.mapDomainToEntity(company);
        CompanyEntity createdCompany = companyJpaRepository.save(companyEntity);
        return CompanyMapper.mapEntityToDomain(createdCompany);
    }

    @Override
    public Page<Company>  getAllCompanies(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CompanyEntity> companiesEntities = companyJpaRepository.findAll(pageable);

        List<Company> domainCompanies = companiesEntities
                .getContent()
                .stream()
                .map(CompanyMapper::mapEntityToDomain)
                .toList();

        return new PageImpl<>(
                domainCompanies,
                pageable,
                companiesEntities.getTotalElements()
        );
    }

    @Override
    public Company getCompanyById(Long id) {
        CompanyEntity companyEntity =  companyJpaRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
        return CompanyMapper.mapEntityToDomain(companyEntity);
    }

    @Override
    public Company updateCompany(Company dto, Long id) {
        CompanyEntity companyEntity = companyJpaRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
        companyEntity.setName(dto.getName());
        CompanyEntity updatedCompanyEntity = companyJpaRepository.save(companyEntity);
        return CompanyMapper.mapEntityToDomain(updatedCompanyEntity);
    }

    @Override
    public Company deleteCompany(Long id) {
        CompanyEntity companyEntity = companyJpaRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
        companyJpaRepository.delete(companyEntity);
        return CompanyMapper.mapEntityToDomain(companyEntity);
    }
}
