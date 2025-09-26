package com.javiervmc.tutorials.companies_keycloak.service.impl;

import com.javiervmc.tutorials.companies_keycloak.dto.*;
import com.javiervmc.tutorials.companies_keycloak.entity.Company;
import com.javiervmc.tutorials.companies_keycloak.exception.company.CompanyNotFoundException;
import com.javiervmc.tutorials.companies_keycloak.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void createCompany_shouldSaveAndReturnDto() {
        CreateCompanyDto dto = new CreateCompanyDto();
        dto.setName("New Company");

        Company company = new Company();
        company.setId(1L);
        company.setName("New Company");

        when(companyRepository.save(any(Company.class))).thenReturn(company);

        CompanyDto result = companyService.createCompany(dto);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("New Company");
        verify(companyRepository).save(any(Company.class));
    }

    @Test
    void getCompanyById_shouldReturnDtoIfExists() {
        Company company = new Company();
        company.setId(1L);
        company.setName("MyCo");

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        CompanyDto result = companyService.getCompanyById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("MyCo");
    }

    @Test
    void getCompanyById_shouldThrowIfNotFound() {
        when(companyRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> companyService.getCompanyById(99L))
                .isInstanceOf(CompanyNotFoundException.class);
    }

    @Test
    void updateCompany_shouldUpdateAndReturnDto() {
        Company existing = new Company();
        existing.setId(1L);
        existing.setName("Old");

        when(companyRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(companyRepository.save(existing)).thenReturn(existing);

        UpdateCompanyDto dto = new UpdateCompanyDto();
        dto.setName("Updated");

        CompanyDto result = companyService.updateCompany(dto, 1L);

        assertThat(result.getName()).isEqualTo("Updated");
        verify(companyRepository).save(existing);
    }

    @Test
    void deleteCompany_shouldDeleteAndReturnDto() {
        Company company = new Company();
        company.setId(1L);
        company.setName("DeleteMe");

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        CompanyDto result = companyService.deleteCompany(1L);

        assertThat(result.getName()).isEqualTo("DeleteMe");
        verify(companyRepository).delete(company);
    }

    @Test
    void getAllCompanies_shouldReturnPagedResponse() {
        Company c1 = new Company();
        c1.setId(1L);
        c1.setName("C1");

        Page<Company> page = new PageImpl<>(List.of(c1), PageRequest.of(0, 10), 1);

        when(companyRepository.findAll(any(Pageable.class))).thenReturn(page);

        GetCompaniesResponse response = companyService.getAllCompanies(0, 10);

        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().getFirst().getName()).isEqualTo("C1");
        assertThat(response.getTotalElements()).isEqualTo(1);
    }
}
