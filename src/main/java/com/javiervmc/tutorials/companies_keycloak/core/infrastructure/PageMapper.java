package com.javiervmc.tutorials.companies_keycloak.core.infrastructure;

import com.javiervmc.tutorials.companies_keycloak.core.domain.PagedResult;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.function.Function;

public class PageMapper {
    // Convierte una Page de Spring (Entidad) a un PagedResult de Dominio (Modelo)
    public static <E, D> PagedResult<D> fromSpringPage(Page<E> springPage, Function<E, D> mapper) {
        List<D> domainContent = springPage.getContent().stream()
                .map(mapper)
                .toList();

        return new PagedResult<>(
                domainContent,
                springPage.getNumber(),
                springPage.getSize(),
                springPage.getTotalElements(),
                springPage.getTotalPages(),
                springPage.isLast()
        );
    }
}