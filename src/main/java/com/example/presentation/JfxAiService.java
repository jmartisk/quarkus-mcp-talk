package com.example.presentation;

import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.mcp.runtime.McpToolBox;
import jakarta.enterprise.context.SessionScoped;

@RegisterAiService
@SessionScoped
public interface JfxAiService {

    @McpToolBox("JFX") // you can combine this with local tools (method annotated with `@Tool`)
    String chat(String query);

}
