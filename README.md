# Quarkus + reveal.js Presentation Template

A Quarkus application that serves a [reveal.js](https://revealjs.com/) slide deck with support for live demos via REST and WebSocket endpoints.

## Running

```bash
./mvnw quarkus:dev
```

Open [http://localhost:8080](http://localhost:8080) to view the slides.

Press **S** to open the speaker notes view.

## Writing Slides

Edit `src/main/resources/META-INF/resources/slides.md` using standard Markdown.

- `---` separates horizontal slides
- `--` separates vertical slides
- `Note:` marks speaker notes

### Slide Features

**Chapter dividers** with colored backgrounds:

```markdown
<!-- .slide: data-background-color="#4595EB" -->

## Chapter Title

Chapter subtitle text.
```

**Key message boxes**:

```markdown
<div class="key-message">Your important takeaway here.</div>
```

**Code blocks** with syntax highlighting:

````markdown
```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello!");
    }
}
```
````

**Images** from the `images/` directory:

```markdown
<img src="images/diagram.png" alt="Description" style="max-height: 400px;">
```

## Live Demos

Embed interactive demo panels directly in slides.

### WebSocket Chat

Add a chat panel connected to the WebSocket endpoint at `/ws/chat`:

```markdown
<div class="demo-panel demo-chat">
    <div class="chat-messages"></div>
    <div class="chat-input-row">
        <input type="text" class="chat-input" placeholder="Type a message...">
        <button class="chat-send">Send</button>
    </div>
</div>
```

Edit `ChatWebSocket.java` to change the chat behavior.

### REST API Demo

Add a panel that calls REST endpoints:

```markdown
<div class="demo-panel demo-rest">
    <div class="rest-result"></div>
    <div class="chat-input-row">
        <input type="text" class="chat-input rest-input" placeholder="Type a message...">
        <button class="chat-send rest-send">Send</button>
    </div>
</div>
```

Edit `DemoResource.java` to add more endpoints, and `demos.js` to wire them up.

## Customizing the Theme

Edit `src/main/resources/META-INF/resources/css/theme.css` to change colors, fonts, and styling.

Key CSS variables:

```css
:root {
    --color-blue: #4595EB;
    --color-red: #FF024A;
    --color-dark: #121212;
    --color-gray: #f5f5f5;
}
```

## Project Structure

```
src/main/
  java/com/example/presentation/
    DemoResource.java        # REST endpoints for demos
    ChatWebSocket.java       # WebSocket endpoint for chat demos
  resources/
    application.properties   # Quarkus configuration
    META-INF/resources/
      index.html             # reveal.js setup
      slides.md              # Slide content (edit this!)
      css/theme.css          # Presentation theme
      js/demos.js            # Demo panel JavaScript
      images/                # Your images go here
```
