package com.ouharri.pharma.model.dto.requests;

import com.ouharri.pharma.model.dto.requests._Request;
import com.ouharri.pharma.model.entities.Medicine;

/**
 * DTO for {@link Medicine}
 */
public record MedicineRequestDto(
        String code,
        String name,
        String dc1,
        double dosage,
        String dosageUnit,
        String form,
        String presentation,
        double ppv,
        String ph,
        double grossPrice,
        String genericPrinciples,
        double reimbursementRate
) implements _Request {
}