package app.core;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.core.MessageController.PayloadCore;
import app.core.websocket.SimpleWebSocketHandler;

@Component
public class MessageReceiver {
    final SimpleWebSocketHandler socketHandler;
    final ObjectMapper objectMapper;

    public MessageReceiver(SimpleWebSocketHandler socketHandler,
            ObjectMapper objectMapper) {
        this.socketHandler = socketHandler;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "backend-response-queue")
    public void handleResponse(
            PayloadCore message) throws JsonProcessingException {
        System.out.println("📥 Resposta recebida do core: " + message);

        String str = objectMapper.writeValueAsString(message);

        socketHandler.broadcastMessage(str);
    }

}
