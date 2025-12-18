package com.javiervmc.tutorials.companies_keycloak.company.application;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCompanyUseCaseTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private UpdateCompanyUseCase updateCompanyUseCase;

    @Test
    void execute_shouldModifyNameAndSave() {
        // GIVEN
        Long id = 1L;
        Company updateData = new Company(null, "Updated Name");

        // The company as it currently exists in the DB
        Company existingCompany = new Company(1L, "Old Name");

        // 1. Mock finding the existing company
        when(companyRepository.getById(id)).thenReturn(existingCompany);

        // 2. Mock saving the modified company
        // We return the same object to simulate the repo returning the persisted entity
        when(companyRepository.save(existingCompany)).thenReturn(existingCompany);

        // WHEN
        Company result = updateCompanyUseCase.execute(updateData, id);

        // THEN
        // 1. Verify we searched for the ID
        verify(companyRepository).getById(id);

        // 2. Verify we saved the object with the NEW name
        // We use argThat to inspect the argument passed to the save method
        verify(companyRepository).save(argThat(company ->
                company.getName().equals("Updated Name") &&
                        company.getId().equals(1L)
        ));

        assertThat(result.getName()).isEqualTo("Updated Name");
    }
}