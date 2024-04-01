package com.ouharri.pharma.model.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto extends AbstractResponse<UUID> {

    @Size(max = 1000)
    String message;

    @NotNull
    Timestamp sentAt;

    boolean sentByUser;
}