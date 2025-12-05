package com.javiervmc.tutorials.companies_keycloak.company.infrastructure;

import com.javiervmc.tutorials.companies_keycloak.core.database.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data // Add getters and setters for all attributes
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="companies")
public class CompanyEntity extends BaseEntity {
    @Id // Make the attribute the PK of the table.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}