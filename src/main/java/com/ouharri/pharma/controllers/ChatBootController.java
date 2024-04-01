package com.ouharri.pharma.controllers;

import com.ouharri.pharma.exceptions.ResourceNotCreatedException;
import com.ouharri.pharma.model.dto.requests.ChatMessageRequestDto;
import com.ouharri.pharma.model.dto.responses.ChatMessageResponseDto;
import com.ouharri.pharma.model.dto.responses.ChatSessionResponseDto;
import com.ouharri.pharma.services.spec.ChatBootService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


/**
 * Controller class for handling ChatBoot-related endpoints.
 *
 * @author <a href="mailto:ouharrioutman@gmail.com">Ouharri Outman</a>
 * @version 1.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/chat-boot")
public class ChatBootController {
    private final ChatBootService service;

    @PostMapping("/session")
    public ResponseEntity<ChatSessionResponseDto> createSession() {
        return new ResponseEntity<>(
                service.createSession(),
                HttpStatus.OK
        );
    }

    @GetMapping("/session/{id}")
    public ResponseEntity<ChatSessionResponseDto> getChatSession(@PathVariable UUID id) {
        return new ResponseEntity<>(
                service.getChatSession(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<ChatSessionResponseDto>> getAllSession() {
        return new ResponseEntity<>(
                service.getAllSession(),
                HttpStatus.OK
        );
    }

    @PostMapping("/send-message")
    public ResponseEntity<ChatMessageResponseDto> sendMessage(
            @Valid @RequestBody ChatMessageRequestDto message,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors())
            throw new ResourceNotCreatedException(bindingResult);

        return new ResponseEntity<>(
                service.sendMessage(message),
                HttpStatus.OK
        );
    }
}
