package com.javiervmc.tutorials.companies_keycloak.company.infrastructure;


import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, Long> {
}
