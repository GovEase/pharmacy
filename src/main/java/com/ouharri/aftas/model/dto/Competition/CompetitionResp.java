package com.ouharri.aftas.model.dto.Competition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ouharri.aftas.model.dto.Address.AddressDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.ouharri.aftas.model.entities.Competition}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CompetitionResp(UUID id, String createdAt, LocalDateTime updatedAt,
                              @Pattern(message = "The code must follow the specified format.", regexp = "^[a-zA-Z]{3}-\\d{2}-\\d{2}-\\d{2}$") String code,
                              @NotNull(message = "The date cannot be null.") Date date,
                              @NotNull(message = "The start time cannot be null.") Time startTime,
                              @NotNull(message = "The end time cannot be null.") Time endTime,
                              @NotNull(message = "The number of participants cannot be null.") @Min(message = "The number of participants must be at least 0.", value = 0) Integer numberOfParticipants,
                              @NotBlank(message = "The location cannot be empty.") String location,
                              @NotNull(message = "The address cannot be null.") AddressDto address,
                              @NotNull(message = "The amount cannot be null.") Double amount
) implements Serializable {
}