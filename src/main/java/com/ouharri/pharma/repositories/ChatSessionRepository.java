package com.ouharri.pharma.repositories;

import com.ouharri.pharma.model.entities.ChatSession;
import com.ouharri.pharma.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, UUID> {
    List<ChatSession> findByUserOrderByCreatedAtDesc(User user);
}