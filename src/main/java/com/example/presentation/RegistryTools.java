package com.example.presentation;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.registryclient.McpRegistryClient;
import dev.langchain4j.mcp.registryclient.model.McpGetServerResponse;
import dev.langchain4j.mcp.registryclient.model.McpRemote;
import dev.langchain4j.mcp.registryclient.model.McpServer;
import dev.langchain4j.mcp.registryclient.model.McpServerListRequest;
import io.quarkiverse.langchain4j.mcp.runtime.McpRegistryClientName;
import io.quarkiverse.langchain4j.mcp.runtime.http.QuarkusStreamableHttpMcpTransport;
import io.quarkus.logging.Log;
import io.vertx.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class RegistryTools {

    @Inject
    @McpRegistryClientName("REGISTRY")
    McpRegistryClient registryClient;

    @Inject
    DynamicMcpClientsToolProvider mcpToolProvider;

    @Inject
    Vertx vertx;

    @Tool("Find available MCP servers by name")
    public List<McpGetServerResponse> listServers(@P("Search a server by name (substring match)") String nameQuery) {
        McpServerListRequest request = McpServerListRequest.builder()
                .search(nameQuery)
                .build();
        return registryClient.listServers(request).getServers();
    }

    @Tool("Install a MCP server")
    public void installMcpServer(@P("The identifier of the server") String name,
                                 @P("The version to install") String version) {
        Log.info("Installing MCP server " + name + " version " + version);
        McpServer server = registryClient.getSpecificServerVersion(name, version).getServer();
        McpRemote remote = server.getRemotes().get(0);
        Log.info("The URL is: " + remote.getUrl());
        McpTransport transport = new QuarkusStreamableHttpMcpTransport.Builder()
                .url(remote.getUrl())
                .httpClient(vertx.createHttpClient())
                .logRequests(true)
                .logResponses(true)
                .build();
        McpClient client = DefaultMcpClient.builder()
                .transport(transport)
                .build();
        mcpToolProvider.addMcpClient(client);
    }

}
