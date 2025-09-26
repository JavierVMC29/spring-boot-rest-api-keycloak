package com.javiervmc.tutorials.companies_keycloak.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javiervmc.tutorials.companies_keycloak.dto.CreateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.dto.UpdateCompanyDto;
import com.javiervmc.tutorials.companies_keycloak.entity.Company;
import com.javiervmc.tutorials.companies_keycloak.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
class CompanyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        companyRepository.deleteAll(); // Clean the database before each test
    }

    @Test
    void createCompany_shouldReturnCreatedCompany() throws Exception {
        CreateCompanyDto dto = new CreateCompanyDto();
        dto.setName("Test Company");

        mockMvc.perform(post("/api/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name", is("Test Company")))
                .andExpect(jsonPath("$.status", is("SUCCESS")))
                .andExpect(jsonPath("$.statusCode", is(201)));
    }

    @Test
    void getCompanies_shouldReturnPagedCompanies() throws Exception {
        // Add a company to the DB
        Company company = new Company();
        company.setName("Company1");
        companyRepository.save(company);

        mockMvc.perform(get("/api/companies")
                        .param("pageNo", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content", hasSize(1)))
                .andExpect(jsonPath("$.data.content[0].name", is("Company1")));
    }

    @Test
    void getCompanyById_shouldReturnCompany() throws Exception {
        Company company = new Company();
        company.setName("Company2");
        Company saved = companyRepository.save(company);

        mockMvc.perform(get("/api/companies/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("Company2")));
    }

    @Test
    void updateCompany_shouldUpdateAndReturnCompany() throws Exception {
        Company company = new Company();
        company.setName("Old Name");
        Company saved = companyRepository.save(company);

        UpdateCompanyDto dto = new UpdateCompanyDto();
        dto.setName("New Name");

        mockMvc.perform(put("/api/companies/{id}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("New Name")));
    }

    @Test
    void deleteCompany_shouldDeleteAndReturnCompany() throws Exception {
        Company company = new Company();
        company.setName("ToDelete");
        Company saved = companyRepository.save(company);

        mockMvc.perform(delete("/api/companies/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("ToDelete")));

        // Confirm it was deleted
        mockMvc.perform(get("/api/companies/{id}", saved.getId()))
                .andExpect(status().isNotFound());
    }
}
