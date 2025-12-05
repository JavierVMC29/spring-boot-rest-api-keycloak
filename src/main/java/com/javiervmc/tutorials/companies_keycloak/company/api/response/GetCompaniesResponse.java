package com.javiervmc.tutorials.companies_keycloak.company.api.response;

import lombok.Data;

import java.util.List;

@Data
public class GetCompaniesResponse {
    private List<CompanyResponse> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}