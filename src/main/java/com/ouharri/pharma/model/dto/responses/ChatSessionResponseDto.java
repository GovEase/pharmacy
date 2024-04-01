package com.ouharri.pharma.model.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ouharri.pharma.model.entities.ChatMessage;
import com.ouharri.pharma.model.entities.ChatSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link ChatSession}
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatSessionResponseDto extends AbstractResponse<UUID> {
    String title;
    UserResponsesDto user;
    List<ChatMessageDto> messages;
}