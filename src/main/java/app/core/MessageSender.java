package app.core;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
    final RabbitTemplate rabbitTemplate;
    final String fila;

    public MessageSender(
            RabbitTemplate rabbitTemplate,
            @Value("${core.queue}") String fila) {
        this.rabbitTemplate = rabbitTemplate;
        this.fila = fila;
    }

    public void sendMessage(MessageController.PayloadCore message) {
        try {
            rabbitTemplate.convertAndSend(fila, message);
            System.out.println("enviado");
        } catch (Exception e) {
            // failover
            System.out.println("falha ao enviar: " + e.getMessage());
        }
    }
}
