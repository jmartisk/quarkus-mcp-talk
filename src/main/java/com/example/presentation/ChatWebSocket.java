package com.example.presentation;

import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;

@WebSocket(path = "/ws/chat")
public class ChatWebSocket {

    public record ChatResponse(String reply, boolean blocked) {}

    @OnTextMessage
    public ChatResponse onMessage(String message) {
        return new ChatResponse("You said: " + message, false);
    }
}
