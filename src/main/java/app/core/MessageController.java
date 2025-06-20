package app.core;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081" })

@RequestMapping("api/votar")
@RestController
public class MessageController {

    final MessageSender sender;

    public MessageController(MessageSender sender) {
        this.sender = sender;
    }

    public record PayloadCore(
            String batchId,
            String sourceNodeId,
            List<Message> dataPoints) {
    }

    // melhor-filme-2025
    public record Message(
            String type,
            String objectIdentifier,
            int valor,
            LocalDateTime datetime) {
    }

    @PostMapping("/candidato/{id}")
    public ResponseEntity<String> votar(
            @PathVariable("id") String idCandidato,
            @RequestBody Message body) {
        System.out.println("votou em: " + idCandidato);

        var payloadCore = new PayloadCore(
                UUID.randomUUID().toString(),
                "node-coletor-fantasma",
                List.of(body));

        sender.sendMessage(payloadCore);
        return ResponseEntity.ok("Mensagem enviada com sucesso");
    }
}
