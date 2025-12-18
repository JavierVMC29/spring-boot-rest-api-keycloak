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

@ExtendWith(MockitoExtension.class)
class DeleteCompanyUseCaseTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private DeleteCompanyUseCase deleteCompanyUseCase;

    @Test
    void execute_shouldReturnDeletedCompany_WhenExists() {
        // GIVEN
        Long id = 1L;
        Company existingCompany = new Company(1L, "To Delete");

        when(companyRepository.getById(id)).thenReturn(existingCompany);

        // WHEN
        Company result = deleteCompanyUseCase.execute(id);

        // THEN
        verify(companyRepository).getById(id);
        verify(companyRepository).delete(id);

        assertThat(result.getName()).isEqualTo("To Delete");
    }
}