package com.ouharri.pharma.model.entities;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacy extends AbstractEntity<UUID> {
    @NotBlank(message = "Le nom de la pharmacie ne peut pas être vide")
    private String name;

    @URL(message = "L'URL de l'image de la pharmacie doit être valide")
    private String image;

    @Embedded
    @NotNull(message = "L'adresse de la pharmacie ne peut pas être vide")
    private Address address;

    @NotBlank(message = "Le numéro de téléphone de la pharmacie ne peut pas être vide")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Le numéro de téléphone doit être composé de 10 chiffres")
    private String phoneNumber;

    @OneToOne(mappedBy = "pharmacy")
    private Pharmacist pharmacist;

    @OneToMany(mappedBy = "pharmacy")
    private List<PharmacistAssistant> PharmacistAssistants;

    @Email(message = "L'adresse e-mail de la pharmacie doit être valide")
    private String email;

    private boolean hasDeliveryService;
}
