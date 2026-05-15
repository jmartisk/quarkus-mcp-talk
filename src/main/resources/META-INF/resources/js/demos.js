document.addEventListener('DOMContentLoaded', () => {
    const slidesContainer = document.querySelector('.reveal .slides');
    if (slidesContainer) {
        new MutationObserver(() => initAllDemos()).observe(slidesContainer, {
            childList: true, subtree: true
        });
    }
    initAllDemos();
});

function initAllDemos() {
    initChatPanels();
    initRestPanels();
}

function initChatPanels() {
    document.querySelectorAll('.demo-chat').forEach(panel => {
        if (panel.dataset.initialized) return;
        panel.dataset.initialized = 'true';

        const input = panel.querySelector('.chat-input');
        const sendBtn = panel.querySelector('.chat-send');
        const messages = panel.querySelector('.chat-messages');

        const wsUrl = 'ws://' + location.host + '/ws/chat';
        const ws = new WebSocket(wsUrl);

        ws.onmessage = (event) => {
            const data = JSON.parse(event.data);
            if (data.blocked) {
                appendMessage(messages, data.reply, 'blocked');
            } else {
                appendMessage(messages, data.reply, 'assistant');
            }
            sendBtn.disabled = false;
        };

        ws.onerror = () => {
            appendMessage(messages, 'WebSocket connection error', 'blocked');
            sendBtn.disabled = false;
        };

        function sendMessage() {
            const text = input.value.trim();
            if (!text || ws.readyState !== WebSocket.OPEN) return;

            appendMessage(messages, text, 'user');
            input.value = '';
            sendBtn.disabled = true;
            ws.send(text);
        }

        sendBtn.addEventListener('click', sendMessage);
        input.addEventListener('keydown', e => {
            if (e.key === 'Enter') sendMessage();
        });
    });
}

function initRestPanels() {
    document.querySelectorAll('.demo-rest').forEach(panel => {
        if (panel.dataset.initialized) return;
        panel.dataset.initialized = 'true';

        const input = panel.querySelector('.rest-input');
        const sendBtn = panel.querySelector('.rest-send');
        const resultDiv = panel.querySelector('.rest-result');

        async function sendRequest() {
            const text = input.value.trim();
            if (!text) return;

            sendBtn.disabled = true;
            appendMessage(resultDiv, text, 'user');
            input.value = '';

            try {
                const res = await fetch(panel.attributes["url"].value, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: text
                });
                const data = await res.json();
                appendMessage(resultDiv, data.message, 'assistant');
            } catch (e) {
                appendMessage(resultDiv, 'Error: ' + e.message, 'blocked');
            }
            sendBtn.disabled = false;
        }

        sendBtn.addEventListener('click', sendRequest);
        input.addEventListener('keydown', e => {
            if (e.key === 'Enter') sendRequest();
        });
    });
}

function simpleMarkdown(text) {
    return text
        .replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>')
        .replace(/\*(.+?)\*/g, '<em>$1</em>')
        .replace(/`(.+?)`/g, '<code>$1</code>')
        .replace(/\n/g, '<br>');
}

function appendMessage(container, text, type) {
    const div = document.createElement('div');
    div.className = 'chat-message ' + type;
    div.innerHTML = simpleMarkdown(text);
    container.appendChild(div);
    container.scrollTop = container.scrollHeight;
}
