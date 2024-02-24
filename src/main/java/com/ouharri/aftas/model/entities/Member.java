package com.ouharri.aftas.model.entities;

import com.ouharri.aftas.model.enums.IdentityDocumentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a member of the sports club.
 * Extends the AbstractEntity class.
 *
 * @author <a href="mailto:ouharrioutman@gmail.com">Ouharri Outman</a>
 * @version 1.0
 */
@Getter
@Setter
@Entity
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member extends User {

    /**
     * The date of accession to the club.
     */
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Accession date cannot be null.")
    private Date accessionDate;

    /**
     * The type of identity document.
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Identity document type cannot be null.")
    private IdentityDocumentType identityDocument;

    /**
     * The identity number of the member.
     */
    @NotBlank(message = "Identity number cannot be blank.")
    private String identityNumber;

    /**
     * The list of huntings associated with this member.
     */
    @OneToMany(
            mappedBy = "huntingCompositeKey.member",
            cascade = CascadeType.ALL
    )
    private List<Hunting> huntings = new ArrayList<>();

    /**
     * The list of rankings associated with this member.
     */
    @OneToMany(
            mappedBy = "id.member",
            cascade = CascadeType.ALL
    )
    private List<Ranking> rankings;
}