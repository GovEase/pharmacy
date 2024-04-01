package com.ouharri.pharma.mapper;

import com.ouharri.pharma.model.dto.requests.ChatMessageRequestDto;
import com.ouharri.pharma.model.dto.requests.UserRequest;
import com.ouharri.pharma.model.dto.responses.ChatMessageResponseDto;
import com.ouharri.pharma.model.dto.responses.UserResponsesDto;
import com.ouharri.pharma.model.entities.ChatMessage;
import com.ouharri.pharma.model.entities.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

/**
 * Mapper interface for converting between {@link UserRequest}, {@link UserResponsesDto}, and {@link User} entities.
 * Extends the generic {@link _Mapper} interface with UUID as the identifier type.
 */
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ChatSessionMapper.class}
)
public interface ChatMessageMapper extends _Mapper<UUID, ChatMessageRequestDto, ChatMessageResponseDto, ChatMessage> {
}
