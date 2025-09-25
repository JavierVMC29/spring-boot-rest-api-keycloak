package com.javiervmc.tutorials.companies_keycloak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data // Add getters and setters for all attributes
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company extends BaseModel{
    @Id // Make the attribute the PK of the table.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}