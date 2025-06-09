package com.sweetsoft.native_ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class WebSocketNativeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WebSocketNativeApplication.class, args);
        Environment env = context.getEnvironment();
        String port = env.getProperty("server.port", "8081");
        
        System.out.println("\n=================================================");
        System.out.println("WebSocket Native Demo 已启动!");
        System.out.println("本地访问地址: http://localhost:" + port);
        System.out.println("WebSocket页面: http://localhost:" + port + "/websocket/test123");
        System.out.println("=================================================\n");
    }
} 