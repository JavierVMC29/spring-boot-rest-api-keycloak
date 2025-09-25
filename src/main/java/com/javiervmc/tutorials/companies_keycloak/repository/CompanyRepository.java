package com.javiervmc.tutorials.companies_keycloak.repository;


import com.javiervmc.tutorials.companies_keycloak.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyRepository extends JpaRepository<Company, Long> {
}
