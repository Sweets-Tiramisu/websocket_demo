package com.sweetsoft.stomp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/send")
    public Map<String,String> sendMessage(@RequestBody MessageRequest request) {
        Map<String, String> map = new HashMap<>();
        try {
            messagingTemplate.convertAndSend("/topic/" + request.getResourceId(), request.getMessage());
            map.put("code", "200");
            map.put("message", "success");
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "500");
            map.put("message", "Exception: " + e.getMessage());
        }
        return map;
    }
}

class MessageRequest {
    private String resourceId;
    private String message;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
} 