package com.javiervmc.tutorials.companies_keycloak.company.application;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyRepository;
import com.javiervmc.tutorials.companies_keycloak.core.domain.PagedResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCompaniesUseCaseTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private GetCompaniesUseCase getCompaniesUseCase;

    @Test
    void execute_shouldReturnPagedResult() {
        // GIVEN
        int page = 0;
        int size = 10;
        PagedResult<Company> pagedResult = new PagedResult<>(
                List.of(new Company(1L, "A")), 0, 10, 1, 1, true
        );

        when(companyRepository.getAll(page, size)).thenReturn(pagedResult);

        // WHEN
        PagedResult<Company> result = getCompaniesUseCase.execute(page, size);

        // THEN
        assertThat(result.getContent()).hasSize(1);
        verify(companyRepository).getAll(page, size);
    }
}