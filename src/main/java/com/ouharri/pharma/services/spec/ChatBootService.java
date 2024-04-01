package com.ouharri.pharma.services.spec;

import com.ouharri.pharma.model.dto.requests.ChatMessageRequestDto;
import com.ouharri.pharma.model.dto.responses.ChatMessageResponseDto;
import com.ouharri.pharma.model.dto.responses.ChatSessionResponseDto;
import com.ouharri.pharma.model.entities.ChatSession;

import java.util.List;
import java.util.UUID;

public interface ChatBootService {

    List<ChatSessionResponseDto> getAllSession();

    ChatMessageResponseDto sendMessage(ChatMessageRequestDto message);

    ChatSessionResponseDto createSession();

    ChatSessionResponseDto getChatSession(UUID id);
}
