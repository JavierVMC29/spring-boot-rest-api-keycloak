package com.javiervmc.tutorials.companies_keycloak.company.application;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyNotFoundException;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCompanyByIdUseCaseTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private GetCompanyByIdUseCase getCompanyByIdUseCase;

    @Test
    void execute_shouldReturnCompany_WhenFound() {
        // GIVEN
        Long id = 1L;
        Company company = new Company(1L, "Found It");
        when(companyRepository.getById(id)).thenReturn(company);

        // WHEN
        Company result = getCompanyByIdUseCase.execute(id);

        // THEN
        assertThat(result.getName()).isEqualTo("Found It");
        verify(companyRepository).getById(id);
    }

    @Test
    void execute_shouldThrowException_WhenNotFound() {
        // GIVEN
        Long id = 99L;
        when(companyRepository.getById(id)).thenThrow(new CompanyNotFoundException(id));

        // WHEN / THEN
        assertThatThrownBy(() -> getCompanyByIdUseCase.execute(id))
                .isInstanceOf(CompanyNotFoundException.class)
                .hasMessageContaining("99");
    }
}