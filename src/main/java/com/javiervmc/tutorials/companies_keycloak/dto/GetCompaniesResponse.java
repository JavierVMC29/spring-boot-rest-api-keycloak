package com.javiervmc.tutorials.companies_keycloak.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetCompaniesResponse {
    private List<CompanyDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}