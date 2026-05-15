package com.example.presentation;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.mcp.client.McpClient;
import io.quarkiverse.langchain4j.mcp.runtime.McpClientName;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.Map;

@Path("/api/demo")
public class ServerDemo {

    @Inject
    @McpClientName("DEMO")
    McpClient mcpClient;

    @POST
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> hello(String name) {
        ToolExecutionRequest toolRequest = ToolExecutionRequest.builder().name("hello").arguments("{\"name\": \"" + name +"\"}").build();
        return Map.of("message", mcpClient.executeTool(toolRequest).resultText());
    }


}
