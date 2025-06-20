package app.core;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageSender {
    final RabbitTemplate rabbitTemplate;
    final String fila;
    ObjectMapper objectMapper;

    public MessageSender(
            RabbitTemplate rabbitTemplate,
            @Value("${core.queue}") String fila,
            ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.fila = fila;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(MessageController.PayloadCore message) {
        try {
            String json = objectMapper.writeValueAsString(message);

            System.out.println("fila: " + fila);
            System.out.println("json");
            System.out.println(json);
            rabbitTemplate.convertAndSend(fila, json);
            System.out.println("enviado");
        } catch (Exception e) {
            // failover
            System.out.println("falha ao enviar: " + e.getMessage());
        }
    }
}
