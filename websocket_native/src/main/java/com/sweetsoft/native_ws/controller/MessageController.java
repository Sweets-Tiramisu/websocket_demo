package com.sweetsoft.native_ws.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sweetsoft.native_ws.handler.WebSocketHandler;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private WebSocketHandler webSocketHandler;

    @PostMapping("/send")
    public Map<String, String> sendMessage(@RequestBody MessageRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            webSocketHandler.broadcastMessage(request.getResourceId(), request.getMessage());
            response.put("code", "200");
            response.put("message", "success");
        } catch (Exception e) {
            response.put("code", "500");
            response.put("message", "Exception: " + e.getMessage());
        }
        return response;
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