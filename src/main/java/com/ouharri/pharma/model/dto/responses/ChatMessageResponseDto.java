package com.ouharri.pharma.model.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ouharri.pharma.model.entities.ChatMessage;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link ChatMessage}
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessageResponseDto extends AbstractResponse<UUID> {

    ChatSessionResponseDto session;

    @Size(max = 1000)
    String message;

    @NotNull
    Timestamp sentAt;

    boolean sentByUser;
}