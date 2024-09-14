package com.example.messagingapp.dto;

import lombok.Data;
import java.time.LocalDateTime; 

@Data
public class MessageDTO {
    private String content;
    private String sender;
    private LocalDateTime timestamp;
}

