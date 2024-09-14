package com.example.messagingapp.controller;

import com.example.messagingapp.dto.MessageDTO;
import com.example.messagingapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageDTO sendMessage(MessageDTO messageDTO) {
        return messageService.saveMessage(messageDTO);
    }

    @GetMapping("/history")
    public List<MessageDTO> getMessageHistory() {
        return messageService.getMessageHistory();
    }
}

