<!-- .slide: data-background-color="#FFFFFF" -->

#  Create and connect to your own MCP server in Java, with Quarkus

*Let your AI-infused application use tools written in Java!*

<br>

**Jan Martiška**, Quarkus Engineer at IBM

Note: !!!Speaker notes go here. They are visible in the presenter view (press S).

---
## About me
<!-- TODO -->

---
<!-- .slide: data-background-color="#4595EB" -->
## Chapter 1: Brief intro about MCP

---
## Model Context Protocol
TODO

---
<!-- .slide: data-background-color="#4595EB" -->
## Chapter 2: Building an MCP server with Quarkus

---
<!-- TODO: mention https://github.com/quarkiverse/quarkus-mcp-servers/ -->
<!-- .slide: data-background-color="#4595EB" -->
## Chapter 3: Connecting an AI-infused Quarkus application to your MCP server

---
## Live Demo: WebSocket Chat

<div>
<div class="demo-panel demo-chat">
    <div class="chat-messages"></div>
    <div class="chat-input-row">
        <input type="text" class="chat-input" placeholder="Type a message...">
        <button class="chat-send">Send</button>
    </div>
</div>
<p>This chat panel connects to the WebSocket endpoint at /ws/chat. Edit ChatWebSocket.java to change the behavior.</p>
</div>

Note: Speaker notes for the WebSocket demo slide.

---

## Live Demo: REST API

<div class="demo-panel demo-rest" url="/api/demo/hello">
    <div class="rest-result"></div>
    <div class="chat-input-row">
        <input type="text" class="chat-input rest-input" placeholder="Type a message to echo...">
        <button class="chat-send rest-send">Send</button>
    </div>
</div>

Note: This panel calls the REST endpoint at /api/demo/echo. Edit DemoResource.java to add more endpoints.

---
<!-- .slide: data-background-color="#4595EB" -->
## Thank You!
Questions?
