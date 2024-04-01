package com.ouharri.pharma.model.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@Entity
@SuperBuilder
//@NoArgsConstructor
@AllArgsConstructor
public class syndicat extends AbstractEntity<UUID> {
}
