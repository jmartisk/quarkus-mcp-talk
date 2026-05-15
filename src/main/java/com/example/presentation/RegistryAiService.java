package com.example.presentation;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;
import jakarta.enterprise.context.SessionScoped;

@RegisterAiService(toolProviderSupplier = DynamicMcpClientsToolProvider.class)
@SessionScoped
@SystemMessage("""
        You have access to tools that search the official MCP registry for MCP servers.
        """)
public interface RegistryAiService {

    @ToolBox(RegistryTools.class)
    String chat(String query);

}
