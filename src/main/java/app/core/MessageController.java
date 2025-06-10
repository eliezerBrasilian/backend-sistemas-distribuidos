package app.core;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081"}) 

@RequestMapping("api/votar")
@RestController
public class MessageController {

    final MessageSender sender;

    public MessageController(MessageSender sender) {
        this.sender = sender;
    }


    public record Message(
    String type,
    String object,
    int valor,
    String datetime
    ){}
    
    @PostMapping("/candidato/{id}")
    public ResponseEntity<String> votar(
        @PathVariable("id") String idCandidato,
        @RequestBody Message body
    ) {
        System.out.println("votou em: " + idCandidato);
        sender.sendMessage(body);
        return ResponseEntity.ok("Mensagem enviada com sucesso");
    }
}
