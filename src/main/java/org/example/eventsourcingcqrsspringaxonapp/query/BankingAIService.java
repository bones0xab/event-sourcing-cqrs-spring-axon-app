package org.example.eventsourcingcqrsspringaxonapp.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.eventsourcingcqrsspringaxonapp.query.dtos.BankAccountResponseDTO;
import org.example.eventsourcingcqrsspringaxonapp.query.handlers.AccountQueryHandler;
import org.example.eventsourcingcqrsspringaxonapp.query.queries.GetAllAccountsQuery;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BankingAIService {
    private final ChatClient.Builder clientBuilder;
    private final AccountQueryHandler accountService;
    private final ObjectMapper objectMapper;

    public String chatAi(String userQuestion) {
        List<BankAccountResponseDTO> accounts = accountService.on(new GetAllAccountsQuery());

        //Convert from accounts types to JSON string
        String tojson = "";
        try {
            tojson = objectMapper.writeValueAsString(accounts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //Template for thinking
        String Systemprompt = """
                Tu es un assistant bancaire intelligent.
                            Tu as accès aux données suivantes au format JSON :
                            {data}
                
                            Réponds à la question de l'utilisateur en utilisant UNIQUEMENT ces données.
                            Si tu ne trouves pas la réponse, dis "Je ne sais pas".
                            Sois poli et concis.
                """;
        //System template
        SystemPromptTemplate template = new SystemPromptTemplate(Systemprompt);


        var systemMessage = template.createMessage(Map.of("data", tojson));


        ChatClient chatclient = clientBuilder.build();
        return chatclient.prompt(new Prompt(List.of(systemMessage, new UserMessage(userQuestion)))).call().content();
    }

}
