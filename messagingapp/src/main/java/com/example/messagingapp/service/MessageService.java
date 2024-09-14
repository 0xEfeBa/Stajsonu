package com.example.messagingapp.service;

import com.example.messagingapp.dto.MessageDTO;
import com.example.messagingapp.model.Message;
import com.example.messagingapp.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageDTO saveMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setSender(messageDTO.getSender());
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
        return messageDTO;
    }

    public List<MessageDTO> getMessageHistory() {
        return messageRepository.findAll()
                .stream()
                // {{ edit_1 }}: MessageDTO constructor should match the parameters
                .map(msg -> new MessageDTO(msg.getContent(), msg.getSender(), msg.getTimestamp().toString())) // Timestamp eklendi
                // {{ edit_2 }}: Adjust if MessageDTO has a different constructor
                .collect(Collectors.toList());
    }
}
