# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What This Is

A Quarkus application that serves a reveal.js slide deck with live demo capabilities via REST and WebSocket endpoints. Slides are written in Markdown and rendered client-side by reveal.js.

## Commands

- **Dev mode:** `./mvnw quarkus:dev` (hot reload, serves on http://localhost:8080)
- **Build:** `./mvnw package`
- **Native build:** `./mvnw package -Dnative`

## Architecture

**Slide delivery:** `index.html` loads reveal.js 5.2.1 (served from mvnpm webjar at `/_static/reveal.js/5.2.1/`) and uses the Markdown plugin to render `slides.md`. Slide separators: `---` (horizontal), `--` (vertical), `Note:` (speaker notes).

**Demo panels:** `demos.js` uses a MutationObserver on the slides container to auto-initialize demo panels as reveal.js dynamically creates slide DOM elements. Two panel types exist:
- `demo-chat` — connects to WebSocket at `/ws/chat` (`ChatWebSocket.java`)
- `demo-rest` — calls REST endpoint `/api/demo/echo` (`DemoResource.java`)

To add a new demo type: add an `init*Panels()` function in `demos.js`, call it from `initAllDemos()`, add corresponding CSS in `theme.css`, and embed the HTML in `slides.md`.

**reveal.js integration:** The `org.mvnpm:reveal.js` dependency (scope `provided`) is served by Quarkus's static resource handler. The `quarkus-web-dependency-locator` extension provides version-free paths (`/_static/reveal.js/` instead of `/_static/reveal.js/5.2.1/`) and generates an importmap at `/_importmap/generated_importmap.js`. The main reveal.js module is imported via the bare specifier `reveal.js`; plugins use version-free paths. The mvnpm repository (`https://repo.mvnpm.org/maven2`) must remain in `pom.xml` for this to resolve. If upgrading reveal.js, only the version in `pom.xml` needs updating.

**WebSocket protocol:** `ChatWebSocket` returns JSON `{"reply": "...", "blocked": boolean}`. The `blocked` field controls CSS styling (red error style vs normal response). Maintain this contract when modifying the endpoint.
