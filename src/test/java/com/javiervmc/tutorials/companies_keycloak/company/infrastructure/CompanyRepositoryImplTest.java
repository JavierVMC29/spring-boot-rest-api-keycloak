package com.javiervmc.tutorials.companies_keycloak.company.infrastructure;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CompanyRepositoryImplTest {

    @Mock
    private CompanyJpaRepository companyJpaRepository;

    @InjectMocks
    private CompanyRepositoryImpl companyRepository;

    @Test
    void createCompany_shouldSaveAndReturnCompany() {
        Company company = new Company();
        company.setName("New Company");

        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(1L);
        companyEntity.setName("New Company");

        when(companyJpaRepository.save(any(CompanyEntity.class))).thenReturn(companyEntity);

        Company result = companyRepository.createCompany(company);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("New Company");
        verify(companyJpaRepository).save(any(CompanyEntity.class));
    }

    @Test
    void getCompanyById_shouldReturnCompanyIfExists() {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(1L);
        companyEntity.setName("MyCo");

        when(companyJpaRepository.findById(1L)).thenReturn(Optional.of(companyEntity));

        Company result = companyRepository.getCompanyById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("MyCo");
    }

    @Test
    void getCompanyById_shouldThrowIfNotFound() {
        when(companyJpaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> companyRepository.getCompanyById(99L))
                .isInstanceOf(CompanyNotFoundException.class);
    }

    @Test
    void updateCompany_shouldUpdateAndReturnDto() {
        CompanyEntity existing = new CompanyEntity();
        existing.setId(1L);
        existing.setName("Old");

        when(companyJpaRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(companyJpaRepository.save(existing)).thenReturn(existing);

        Company company = new Company();
        company.setName("Updated");

        Company result = companyRepository.updateCompany(company, 1L);

        assertThat(result.getName()).isEqualTo("Updated");
        verify(companyJpaRepository).save(existing);
    }

    @Test
    void deleteCompany_shouldDeleteAndReturnDto() {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(1L);
        companyEntity.setName("DeleteMe");

        when(companyJpaRepository.findById(1L)).thenReturn(Optional.of(companyEntity));

        Company result = companyRepository.deleteCompany(1L);

        assertThat(result.getName()).isEqualTo("DeleteMe");
        verify(companyJpaRepository).delete(companyEntity);
    }

    @Test
    void getAllCompanies_shouldReturnPagedResponse() {
        CompanyEntity c1 = new CompanyEntity();
        c1.setId(1L);
        c1.setName("C1");

        Page<CompanyEntity> page = new PageImpl<>(List.of(c1), PageRequest.of(0, 10), 1);

        when(companyJpaRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Company> response = companyRepository.getAllCompanies(0, 10);

        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().getFirst().getName()).isEqualTo("C1");
        assertThat(response.getTotalElements()).isEqualTo(1);
    }
}
