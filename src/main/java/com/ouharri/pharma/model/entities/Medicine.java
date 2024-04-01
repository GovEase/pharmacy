package com.ouharri.pharma.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Medicine extends AuditableEntity implements _Entity<String> {
    @Id
    private String code;
    private String name;
    private String dc1;
    private double dosage;
    private String dosageUnit;
    private String form;
    private String presentation;
    private double ppv;
    private String ph;
    private double grossPrice;
    private String genericPrinciples;
    private double reimbursementRate;

    /**
     * Gets the unique identifier of the entity.
     *
     * @return The entity's identifier.
     */
    public String getId() {
        return code;
    }
}
