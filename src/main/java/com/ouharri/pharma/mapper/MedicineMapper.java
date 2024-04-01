package com.ouharri.pharma.mapper;

import com.ouharri.pharma.model.dto.responses.MedicineResponseDto;
import com.ouharri.pharma.model.entities.Medicine;
import com.ouharri.pharma.model.dto.requests.MedicineRequestDto;
import org.mapstruct.*;

/**
 * Mapper interface for converting between {@link com.ouharri.pharma.model.dto.requests.UserRequest}, {@link com.ouharri.pharma.model.dto.responses.UserResponsesDto}, and {@link com.ouharri.pharma.model.entities.User} entities.
 * Extends the generic {@link _Mapper} interface with UUID as the identifier type.
 */
/**
 * Mapper interface for converting between {@link Medicine} DTOs and entities.
 * Uses MapStruct for automatic mapping implementation.
 */
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface MedicineMapper extends _Mapper<String, MedicineRequestDto, MedicineResponseDto, Medicine>{
}
