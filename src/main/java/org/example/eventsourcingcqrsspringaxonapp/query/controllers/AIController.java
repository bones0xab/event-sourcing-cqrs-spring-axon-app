package org.example.eventsourcingcqrsspringaxonapp.query.controllers;

import lombok.AllArgsConstructor;
import org.example.eventsourcingcqrsspringaxonapp.query.BankingAIService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/query/ai")
@AllArgsConstructor
@CrossOrigin("*")
public class AIController {
    private final BankingAIService bankingAIService;

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String , String > request) {
        String question = request.get("question");
        String answer = bankingAIService.chatAi(question);
        return Map.of("response", answer);
    }
}
