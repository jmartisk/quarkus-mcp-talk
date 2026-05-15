package com.example.presentation;

import io.quarkiverse.mcp.server.Tool;

public class McpServer {

    @Tool
    public String hello(String name) {
        return "Hello " + name;
    }

}
