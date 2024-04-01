package com.ouharri.pharma.model.dto.requests;

import com.ouharri.pharma.model.dto.basic.AddressDto;
import com.ouharri.pharma.model.dto.requests._Request;
import com.ouharri.pharma.model.dto.responses.PharmacistAssistantResponseDto;
import com.ouharri.pharma.model.dto.responses.PharmacistResponseDto;
import com.ouharri.pharma.model.entities.Pharmacy;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

import java.util.List;

/**
 * DTO for {@link Pharmacy}
 */
@Value
public class PharmacyRequestDto implements _Request {
    @NotBlank(message = "Le nom de la pharmacie ne peut pas être vide")
    String name;

    @URL(message = "L'URL de l'image de la pharmacie doit être valide")
    String image;

    @NotNull(message = "L'adresse de la pharmacie ne peut pas être vide")
    AddressDto address;

    @Pattern(message = "Le numéro de téléphone doit être composé de 10 chiffres", regexp = "(^$|[0-9]{10})")
    @NotBlank(message = "Le numéro de téléphone de la pharmacie ne peut pas être vide")
    String phoneNumber;

    PharmacistResponseDto pharmacist;

    List<PharmacistAssistantResponseDto> PharmacistAssistants;

    @Email(message = "L'adresse e-mail de la pharmacie doit être valide")
    String email;

    boolean hasDeliveryService;
}