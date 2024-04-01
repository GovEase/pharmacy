package com.ouharri.pharma.model.dto.requests;

import com.ouharri.pharma.model.dto.responses.ChatSessionResponseDto;
import com.ouharri.pharma.model.entities.ChatMessage;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link ChatMessage}
 */
public record ChatMessageRequestDto(
        ChatSessionResponseDto session,
        @Size(max = 1000) String message
) implements _Request {
}