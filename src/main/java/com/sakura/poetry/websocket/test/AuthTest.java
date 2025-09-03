package com.sakura.poetry.websocket.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakura.poetry.dto.UserLoginDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * 认证测试类
 * 
 * <p>用于测试用户登录功能。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public class AuthTest {

    public static void main(String[] args) {
        try {
            // 创建HTTP客户端
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            // 创建登录请求数据
            UserLoginDTO loginDTO = new UserLoginDTO();
            loginDTO.setUsername("sakura");
            loginDTO.setPassword("123456");

            // 将对象转换为JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(loginDTO);

            // 创建HTTP请求
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // 发送请求并获取响应
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 输出结果
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}