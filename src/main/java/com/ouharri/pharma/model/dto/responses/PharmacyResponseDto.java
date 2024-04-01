package com.ouharri.pharma.model.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ouharri.pharma.model.dto.basic.AddressDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.URL;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PharmacyResponseDto extends AbstractResponse<UUID> {

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
