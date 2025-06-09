package com.sweetsoft.native_ws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
} 