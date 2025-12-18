package com.javiervmc.tutorials.companies_keycloak.company.infrastructure;

import com.javiervmc.tutorials.companies_keycloak.company.domain.Company;
import com.javiervmc.tutorials.companies_keycloak.company.domain.CompanyNotFoundException;
import com.javiervmc.tutorials.companies_keycloak.core.domain.PagedResult;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CompanyRepositoryImplTest {

    @Mock
    private CompanyJpaRepository companyJpaRepository;

    @InjectMocks
    private CompanyRepositoryImpl companyRepository;

    @Test
    void save_shouldPersistAndReturnCompany_WhenCreatingNew() {
        // GIVEN
        // A company without ID (new)
        Company company = new Company();
        company.setName("New Company");

        // The entity returned by JPA after save (with ID generated)
        CompanyEntity savedEntity = new CompanyEntity();
        savedEntity.setId(1L);
        savedEntity.setName("New Company");

        when(companyJpaRepository.save(any(CompanyEntity.class))).thenReturn(savedEntity);

        // WHEN
        Company result = companyRepository.save(company);

        // THEN
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("New Company");
        verify(companyJpaRepository).save(any(CompanyEntity.class));
    }

    @Test
    void save_shouldUpdateAndReturnCompany_WhenUpdatingExisting() {
        // GIVEN
        // A company WITH ID (existing being updated)
        Company company = new Company(1L, "Updated Name");

        CompanyEntity updatedEntity = new CompanyEntity(1L, "Updated Name");

        when(companyJpaRepository.save(any(CompanyEntity.class))).thenReturn(updatedEntity);

        // WHEN
        Company result = companyRepository.save(company);

        // THEN
        assertThat(result.getName()).isEqualTo("Updated Name");
        assertThat(result.getId()).isEqualTo(1L);
        // Note: We don't need to mock findById here anymore because
        // the generic repository implementation just calls jpa.save()
        verify(companyJpaRepository).save(any(CompanyEntity.class));
    }

    @Test
    void getById_shouldReturnCompany_WhenExists() {
        // GIVEN
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(1L);
        companyEntity.setName("MyCo");

        when(companyJpaRepository.findById(1L)).thenReturn(Optional.of(companyEntity));

        // WHEN
        Company result = companyRepository.getById(1L);

        // THEN
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("MyCo");
    }

    @Test
    void getById_shouldThrowException_WhenNotFound() {
        // GIVEN
        when(companyJpaRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> companyRepository.getById(99L))
                .isInstanceOf(CompanyNotFoundException.class);
    }

    @Test
    void delete_shouldRemoveCompany_WhenExists() {
        // GIVEN
        when(companyJpaRepository.existsById(1L)).thenReturn(true);

        // WHEN
        companyRepository.delete(1L);

        // THEN
        verify(companyJpaRepository).deleteById(1L);
    }

    @Test
    void delete_shouldThrowException_WhenNotFound() {
        // GIVEN
        when(companyJpaRepository.existsById(99L)).thenReturn(false);

        // WHEN / THEN
        assertThatThrownBy(() -> companyRepository.delete(99L))
                .isInstanceOf(CompanyNotFoundException.class);

        // Ensure deleteById was NEVER called to be safe
        verify(companyJpaRepository, never()).deleteById(any());
    }

    @Test
    void getAll_shouldReturnPagedResponse() {
        // GIVEN
        CompanyEntity c1 = new CompanyEntity();
        c1.setId(1L);
        c1.setName("C1");

        Page<CompanyEntity> page = new PageImpl<>(List.of(c1), PageRequest.of(0, 10), 1);

        when(companyJpaRepository.findAll(any(Pageable.class))).thenReturn(page);

        // WHEN
        PagedResult<Company> response = companyRepository.getAll(0, 10);

        // THEN
        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().getFirst().getName()).isEqualTo("C1");
        assertThat(response.getTotalElements()).isEqualTo(1);
    }
}