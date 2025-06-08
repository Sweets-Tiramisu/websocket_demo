package com.sweetsoft.websocket_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class WebSocketWebApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WebSocketWebApplication.class, args);
        Environment env = context.getEnvironment();
        String port = env.getProperty("server.port", "8080");
        
        System.out.println("\n=================================================");
        System.out.println("WebSocket Demo 已启动!");
        System.out.println("本地访问地址: http://localhost:" + port);
        System.out.println("WebSocket页面: http://localhost:" + port + "/websocket/test123");
        System.out.println("网关WebSocket页面: http://localhost:" + port + "/websocket/gateway/test123");
        System.out.println("=================================================\n");
    }
} 