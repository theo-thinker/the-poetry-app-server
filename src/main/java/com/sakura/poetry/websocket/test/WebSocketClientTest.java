package com.sakura.poetry.websocket.test;

import com.sakura.poetry.websocket.util.JwtUtil;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * WebSocket客户端测试类
 * 
 * <p>用于测试WebSocket连接和消息发送功能。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Component
public class WebSocketClientTest implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // 生成测试用户令牌
        String token = JwtUtil.generateToken(1L, "testuser");
        System.out.println("Generated token: " + token);
        
        // 构造WebSocket连接URL
        String wsUrl = "ws://localhost:8080/ws/chat?token=" + token;
        System.out.println("WebSocket URL: " + wsUrl);
        
        try {
            // 创建WebSocket客户端
            WebSocketClient client = new WebSocketClient(new URI(wsUrl)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected to WebSocket server");
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Received message: " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Connection closed: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    System.err.println("WebSocket error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            };
            
            // 连接WebSocket服务器
            client.connect();
            
            // 等待连接建立
            Thread.sleep(2000);
            
            // 发送测试消息
            if (client.isOpen()) {
                // 发送心跳消息
                String heartbeatMessage = "{\n" +
                        "  \"type\": \"heartbeat\",\n" +
                        "  \"content\": \"ping\"\n" +
                        "}";
                client.send(heartbeatMessage);
                System.out.println("Sent heartbeat message");
                
                // 等待响应
                Thread.sleep(2000);
                
                // 关闭连接
                client.close();
                System.out.println("WebSocket connection closed");
            }
        } catch (URISyntaxException e) {
            System.err.println("Invalid WebSocket URL: " + e.getMessage());
        }
    }
}