package com.javiervmc.tutorials.companies_keycloak.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResult<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;

    // Método utilitario para mapear el contenido
    // Esto es útil si quieres transformar PagedResult<Entity> a PagedResult<Dto>
    public <R> PagedResult<R> map(java.util.function.Function<T, R> mapper) {
        List<R> mappedContent = content.stream().map(mapper).toList();
        return new PagedResult<>(mappedContent, pageNumber, pageSize, totalElements, totalPages, isLast);
    }
}