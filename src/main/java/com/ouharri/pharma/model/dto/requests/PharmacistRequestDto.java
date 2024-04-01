package com.ouharri.pharma.model.dto.requests;

import com.ouharri.pharma.model.entities.Pharmacist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link Pharmacist}
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PharmacistRequestDto extends UserRequest {
    String matricule;
}