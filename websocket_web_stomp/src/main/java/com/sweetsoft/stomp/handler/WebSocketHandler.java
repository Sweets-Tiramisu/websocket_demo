package com.sweetsoft.stomp.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketHandler {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send")
    public void sendMessage(@Payload String message, SimpMessageHeaderAccessor headerAccessor) {
        String resourceId = headerAccessor.getFirstNativeHeader("resourceId");
        if (resourceId != null) {
            messagingTemplate.convertAndSend("/topic/" + resourceId, message);
        }
    }
} 