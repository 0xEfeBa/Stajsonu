package com.example.messagingapp.repository;

import com.example.messagingapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
