package app.core;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import app.core.websocket.SimpleWebSocketHandler;

@Component
public class MessageReceiver {
    final SimpleWebSocketHandler socketHandler;

    public MessageReceiver(SimpleWebSocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @RabbitListener(queues = "backend-response-queue")
    public void handleResponse(
            String message) {
        System.out.println("📥 Resposta recebida do core: " + message);
        socketHandler.broadcastMessage(message);
    }

}
