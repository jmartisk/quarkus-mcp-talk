package com.example.presentation;

import io.quarkiverse.mcp.server.McpLog;
import io.quarkiverse.mcp.server.Tool;

public class McpServer {

    @Tool
    public String hello(String name) {
        return "Hello " + name;
    }

    @Tool
    public String logDemo(McpLog log) {
        log.send(McpLog.LogLevel.ALERT, "This is a log entry");
        return "success";
    }

}
