package com.sweetsoft.native_ws.handler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String resourceId = getResourceId(session);
        if (resourceId != null) {
            sessions.put(resourceId, session);
            log.info("WebSocket连接已建立 - 资源ID: {}", resourceId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String resourceId = getResourceId(session);
        if (resourceId != null) {
            // 广播消息给所有订阅该资源的客户端
            broadcastMessage(resourceId, message.getPayload());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String resourceId = getResourceId(session);
        if (resourceId != null) {
            sessions.remove(resourceId);
            log.info("WebSocket连接已关闭 - 资源ID: {}", resourceId);
        }
    }

    private String getResourceId(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null && query.contains("resourceId=")) {
            return query.split("resourceId=")[1];
        }
        return null;
    }

    public void broadcastMessage(String resourceId, String message) {
        WebSocketSession session = sessions.get(resourceId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.error("发送消息失败 - 资源ID: {}, 错误: {}", resourceId, e.getMessage());
            }
        }
    }
} 