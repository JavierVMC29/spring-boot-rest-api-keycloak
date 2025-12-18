package com.javiervmc.tutorials.companies_keycloak.company.infrastructure;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyNotFoundException;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyRepository;
import com.javiervmc.tutorials.companies_keycloak.core.domain.PagedResult;
import com.javiervmc.tutorials.companies_keycloak.core.infrastructure.PageMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyRepositoryImpl implements CompanyRepository {
    private final CompanyJpaRepository companyJpaRepository;

    @Autowired
    public CompanyRepositoryImpl(CompanyJpaRepository companyJpaRepository) {
        this.companyJpaRepository = companyJpaRepository;
    }

    @Override
    public Company save(Company company) {
        CompanyEntity companyEntity = CompanyMapper.mapDomainToEntity(company);

        // JPA Logic:
        // If companyEntity.getId() is null -> INSERT
        // If companyEntity.getId() exists -> UPDATE
        CompanyEntity savedEntity = companyJpaRepository.save(companyEntity);

        return CompanyMapper.mapEntityToDomain(savedEntity);
    }

    @Override
    public Company getById(Long id) {
        CompanyEntity companyEntity = companyJpaRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(id));
        return CompanyMapper.mapEntityToDomain(companyEntity);
    }

    @Override
    public PagedResult<Company> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CompanyEntity> springPage = companyJpaRepository.findAll(pageable);

        return PageMapper.fromSpringPage(springPage, CompanyMapper::mapEntityToDomain);
    }

    @Override
    public void delete(Long id) {
        // We verify existence first (Good practice)
        if (!companyJpaRepository.existsById(id)) {
            throw new CompanyNotFoundException(id);
        }
        companyJpaRepository.deleteById(id);
    }
}