package com.ouharri.pharma.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouharri.pharma.exceptions.NoAuthenticateUser;
import com.ouharri.pharma.exceptions.ResourceNotFoundException;
import com.ouharri.pharma.mapper.ChatMessageMapper;
import com.ouharri.pharma.mapper.ChatSessionMapper;
import com.ouharri.pharma.mapper.UserMapper;
import com.ouharri.pharma.model.dto.requests.ChatMessageRequestDto;
import com.ouharri.pharma.model.dto.responses.ChatMessageResponseDto;
import com.ouharri.pharma.model.dto.responses.ChatSessionResponseDto;
import com.ouharri.pharma.model.entities.ChatMessage;
import com.ouharri.pharma.model.entities.ChatSession;
import com.ouharri.pharma.model.entities.User;
import com.ouharri.pharma.repositories.ChatMessageRepository;
import com.ouharri.pharma.repositories.ChatSessionRepository;
import com.ouharri.pharma.services.spec.ChatBootService;
import com.ouharri.pharma.services.spec.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Implementation of the ChatBootService interface.
 *
 * @author <a href="mailto:ouharrioutman@gmail.com">ouharri outman</a>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatBootServiceImpl implements ChatBootService {

    private final ChatClient chatClient;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final UserMapper userMapper;
    private final ModelMapper modelMapper;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final ChatSessionMapper chatSessionMapper;

    /**
     * Creates a new chat session.
     *
     * @return The created chat session response DTO.
     * @throws NoAuthenticateUser if the user is not authenticated.
     */
    public ChatSessionResponseDto createSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken)
            throw new NoAuthenticateUser("User not authenticated");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername());

        ChatSession session = ChatSession.builder().user(user).build();

        return chatSessionMapper.toResponse(chatSessionRepository.saveAndFlush(session));
    }

    /**
     * Retrieves all chat sessions for the authenticated user.
     *
     * @return List of chat session response DTOs.
     * @throws NoAuthenticateUser if the user is not authenticated.
     */
    public List<ChatSessionResponseDto> getAllSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken)
            throw new NoAuthenticateUser("User not authenticated");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername());

        List<ChatSession> session = chatSessionRepository.findByUserOrderByCreatedAtDesc(user);

        return chatSessionMapper.toResponse(session);
    }


    /**
     * Creates a new chat session with the provided user.
     *
     * @param user The user for the session.
     * @return The created chat session.
     */

    public ChatSession createSession(User user, String message) {
        AtomicReference<StringBuilder> prompt = new AtomicReference<>(new StringBuilder());

        prompt.get().append("""
                        You are a virtual medical assistant specialized in health and medicine. Your role is to assist users in understanding health issues and providing reliable and accurate medical information.

                        Based on the following message:
                        "{message}"

                        Please provide a direct title or general subject related to health or medicine that best summarizes the user's message without repeating the question,without repeating mentioning instructions, without repeating any prefaces or labels like: "
                        """)
                .append(user.getFirstname())
                .append("""
                        : " or "Tobib: " or "General Subject: "... >
                        """);

        PromptTemplate promptTemplate = new PromptTemplate(prompt.toString());
        promptTemplate.add("message", message);
        ChatResponse response = chatClient.call(promptTemplate.create());

        String title = response.getResult().getOutput().getContent().trim();

        ChatSession session = ChatSession.builder()
                .user(user)
                .title(title)
                .build();

        return chatSessionRepository.saveAndFlush(session);
    }


    /**
     * Retrieves a chat session by its ID.
     *
     * @param id The ID of the chat session to retrieve.
     * @return The chat session response DTO.
     * @throws ResourceNotFoundException if the chat session is not found.
     */
    public ChatSessionResponseDto getChatSession(UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken)
            throw new NoAuthenticateUser("User not authenticated");

        ChatSession chatSession = chatSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ChatSession not found with id: " + id));
        return modelMapper.map(chatSession, ChatSessionResponseDto.class);
    }

    /**
     * Sends a message in the chat.
     *
     * @param request The chat message request DTO.
     * @return The chat message response DTO.
     */
    @Transactional
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto request) {
        PromptTemplate promptTemplate;
        ChatMessage chatMessage = chatMessageMapper.toEntityFromRequest(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByEmail(userDetails.getUsername());

            if (request.session() == null || request.session().getUser() == null)
                chatMessage.setSession(createSession(user, request.message()));
            else {
                Optional<ChatSession> session = chatSessionRepository.findById(request.session().getId());
                if (session.isPresent())
                    chatMessage.setSession(session.get());
                else
                    chatMessage.setSession(createSession(user, request.message()));
            }

            chatMessage.setSentByUser(true);
            promptTemplate = createSessionPrompt(request.message(), chatMessage.getSession());
        } else {
            chatMessage.setSentByUser(false);
            promptTemplate = createPrompt(request.message());
        }

        chatMessage.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
        ChatMessage message = chatMessageRepository.saveAndFlush(chatMessage);

        ChatResponse response = chatClient.call(promptTemplate.create());

        ChatMessage chatBotResponse = chatMessageRepository.saveAndFlush(
                ChatMessage.builder()
                        .session(message.getSession())
                        .message(response.getResult().getOutput().getContent())
                        .sentAt(Timestamp.valueOf(LocalDateTime.now()))
                        .sentByUser(false)
                        .build()
        );

        return chatMessageMapper.toResponse(chatBotResponse);
    }

    /**
     * Creates a prompt template for user messages.
     *
     * @param message The user message.
     * @return The prompt template.
     */
    @NotNull
    private PromptTemplate createPrompt(String message) {

        PromptTemplate promptTemplate = new PromptTemplate("""
                Welcome to Tobib, your trusted personal medical assistant!
                        
                Tobib is an AI-powered chatbot designed to provide accurate and evidence-based information on medical and health topics. Whether you have questions about symptoms, medications, medical conditions, or any other health-related subject, Tobib is here to assist you with reliable and up-to-date information.
                        
                Role of Tobib:
                        
                Tobib is trained using the latest medical knowledge and guidelines from reputable sources. It can offer general health advice, provide accurate information about medications and their effects, define medical terms, and guide you in finding qualified healthcare professionals near you. Tobib is committed to protecting your privacy and will not disclose any personal information.
                        
                Human: {user_message}
                                
                Answers the Human's last question
                        
                <If the user's query is related to health or medicine, provide a direct, comprehensive, and evidence-based response in the same language as the question, without repeating the question, mentioning instructions, or using any prefaces or labels. If necessary, request clarification or additional details to provide the most relevant and helpful response.

                If the user's query is not related to health or medicine, politely inform them that you are a medical assistant and cannot provide information on unrelated topics.>
                        
                Feel free to ask me any other questions you may have! I'm here to provide reliable medical information and guidance.
                """);
        promptTemplate.add("user_message", message);

        return promptTemplate;
    }

    /**
     * Creates a prompt template for authenticated user messages within a session.
     *
     * @param message The user message.
     * @param session The chat session.
     * @return The prompt template.
     */
    @NotNull
    private PromptTemplate createSessionPrompt(String message, ChatSession session) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                Welcome to Tobib, your trusted personal medical assistant!
                        
                Tobib is an AI-powered chatbot designed to provide accurate and evidence-based information on medical and health topics. Whether you have questions about symptoms, medications, medical conditions, or any other health-related subject, Tobib is here to assist you with reliable and up-to-date information.
                        
                Role of Tobib:
                        
                Tobib is trained using the latest medical knowledge and guidelines from reputable sources. It can offer general health advice, provide accurate information about medications and their effects, define medical terms, and guide you in finding qualified healthcare professionals near you. Tobib is committed to protecting your privacy and will not disclose any personal information.
                                
                """);

        prompt.append("Hello ")
                .append(session.getUser().getFirstname())
                .append("! How can I assist you today with any health or medical-related questions? I'm here to provide reliable and evidence-based information. \n\n");

        if (session.getMessages() != null && !session.getMessages().isEmpty())
            for (ChatMessage m : session.getMessages())
                if (m.isSentByUser())
                    prompt.append(session.getUser().getFirstname())
                            .append(" ")
                            .append(session.getUser().getLastname())
                            .append(": ")
                            .append(m.getMessage())
                            .append("\n\n");
                else
                    prompt.append("Tobib: ")
                            .append(m.getMessage())
                            .append("\n\n");

        prompt.append(session.getUser().getFirstname())
                .append(" ")
                .append(session.getUser().getLastname());

        prompt.append("""
                : {user_message}
                                
                Answers\s
                """);

        prompt.append(session.getUser().getFirstname())
                .append("'s last question");

        prompt.append("""

                <If the user's query is related to health or medicine, provide a direct, comprehensive, and evidence-based response in the same language as the question, without repeating the question, mentioning instructions, or using any prefaces or labels like\s
                """);

        prompt.append(session.getUser().getFirstname())
                .append(": \" or \"Tobib: \". ")
                .append("""
                        If necessary, request clarification or additional details to provide the most relevant and helpful response. You may use the user information below to personalize your response.

                        If the user's query is not related to health or medicine, politely inform them that you are a medical assistant and cannot provide information on unrelated topics.>

                        Here is the information from the user who is speaking with you:
                        {user_info}

                        Feel free to ask me any other questions you may have! I'm here to provide reliable medical information and guidance.
                        """);

        PromptTemplate promptTemplate = new PromptTemplate(prompt.toString());

        try {
            String userJson = objectMapper.writeValueAsString(userMapper.toResponse(session.getUser()));

            promptTemplate.add("user_message", message);
            promptTemplate.add("user_info", userJson);

            System.out.println(promptTemplate.getTemplate());

            return promptTemplate;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
