package com.example.presentation;

import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import jakarta.inject.Inject;

@WebSocket(path = "/ws/jfx-demo")
public class JfxDemoWebSocket {

    public record ChatResponse(String reply, boolean blocked) {}

    @Inject
    JfxAiService jfxAiService;

    @OnTextMessage
    public ChatResponse onMessage(String message) {
        String response = jfxAiService.chat(message);
        return new ChatResponse(response, false);
    }
}