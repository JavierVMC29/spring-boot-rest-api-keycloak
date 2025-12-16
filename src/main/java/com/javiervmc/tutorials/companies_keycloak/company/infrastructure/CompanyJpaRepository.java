package com.javiervmc.tutorials.companies_keycloak.company.infrastructure;


import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, Long> {
}
