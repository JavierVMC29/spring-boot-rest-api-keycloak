package com.javiervmc.tutorials.companies_keycloak.company.application;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // JUnit 5 + Mockito puro (Sin Spring Context)
class CreateCompanyUseCaseTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CreateCompanyUseCase createCompanyUseCase;

    @Test
    void execute_shouldSaveAndReturnCompany() {
        // GIVEN
        Company inputCompany = new Company(null, "New Corp");
        Company savedCompany = new Company(1L, "New Corp");

        when(companyRepository.save(inputCompany)).thenReturn(savedCompany);

        // WHEN
        Company result = createCompanyUseCase.execute(inputCompany);

        // THEN
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("New Corp");

        verify(companyRepository).save(inputCompany);
    }
}