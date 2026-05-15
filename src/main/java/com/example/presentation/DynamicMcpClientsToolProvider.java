package com.example.presentation;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.service.tool.ToolProvider;
import io.quarkus.logging.Log;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SessionScoped
public class DynamicMcpClientsToolProvider implements Supplier<ToolProvider> {

    private final McpToolProvider provider;

    private List<McpClient> clients = new ArrayList<>();

    public DynamicMcpClientsToolProvider() {
        this.provider = McpToolProvider.builder()
                .mcpClients(new ArrayList<>())
                .build();
    }

    @Override
    public ToolProvider get() {
        return provider;
    }

    public void addMcpClient(McpClient client) {
        this.provider.addMcpClient(client);
        this.clients.add(client);
    }

    @PreDestroy
    public void cleanup() {
        Log.info("Cleaning up all clients");
        for (McpClient client : this.clients) {
            try {
                client.close();
            } catch (Exception e) {
            }
        }
    }

}
