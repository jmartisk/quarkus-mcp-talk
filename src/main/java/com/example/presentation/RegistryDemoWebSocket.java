package com.example.presentation;

import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import jakarta.inject.Inject;

@WebSocket(path = "/ws/registry-demo")
public class RegistryDemoWebSocket {

    public record ChatResponse(String reply, boolean blocked) {}

    @Inject
    RegistryAiService registryAiService;

    @OnTextMessage
    public ChatResponse onMessage(String message) {
        String response = registryAiService.chat(message);
        return new ChatResponse(response, false);
    }
}