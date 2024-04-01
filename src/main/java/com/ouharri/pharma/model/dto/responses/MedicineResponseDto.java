package com.ouharri.pharma.model.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ouharri.pharma.model.entities.Medicine;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link Medicine}
 */
@Getter
@Setter
@SuperBuilder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicineResponseDto extends AuditableResponse{
    String code;
    String name;
    String dc1;
    double dosage;
    String dosageUnit;
    String form;
    String presentation;
    double ppv;
    String ph;
    double grossPrice;
    String genericPrinciples;
    double reimbursementRate;
}