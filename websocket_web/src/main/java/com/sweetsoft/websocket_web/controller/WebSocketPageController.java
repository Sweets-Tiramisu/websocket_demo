package com.sweetsoft.websocket_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebSocketPageController {
    
    @GetMapping("/websocket")
    public String websocketPage() {
        return "index";
    }
    
    @GetMapping("/websocket/{resourceId}")
    public String websocketPageWithId() {
        return "index";
    }
    
    @GetMapping("/gateway")
    public String gatewayPage() {
        return "gateway";
    }
    
    @GetMapping("/gateway/{resourceId}")
    public String gatewayPageWithId() {
        return "gateway";
    }
} 