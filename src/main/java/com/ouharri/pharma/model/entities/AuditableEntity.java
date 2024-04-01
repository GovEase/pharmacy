package com.ouharri.pharma.model.entities;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Base class for all entity classes requiring audit information.
 * Includes fields for tracking the creation and last update timestamps,
 * and a version field for optimistic locking in database transactions.
 *
 * @author <a href="mailto:ouharrioutman@gmail.com">Ouharri Outman</a>
 */
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class AuditableEntity implements Serializable {

    /**
     * The timestamp indicating when the entity was created.
     */
    @CreationTimestamp
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    /**
     * The timestamp indicating when the entity was last updated.
     */
    @UpdateTimestamp
    private Timestamp updatedAt = Timestamp.valueOf(LocalDateTime.now());

    /**
     * The version of the entity, used for optimistic locking.
     */
    @Version
    @ReadOnlyProperty
    private Long version = 0L;
}
