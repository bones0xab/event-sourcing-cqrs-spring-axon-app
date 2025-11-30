package org.example.eventsourcingcqrsspringaxonapp.query.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.eventsourcingcqrsspringaxonapp.query.dtos.AccountOperationResponseDTO;
import org.example.eventsourcingcqrsspringaxonapp.query.dtos.BankAccountResponseDTO;
import org.example.eventsourcingcqrsspringaxonapp.query.entities.AccountOperation;
import org.example.eventsourcingcqrsspringaxonapp.query.queries.GetAccountByIdQuery;
import org.example.eventsourcingcqrsspringaxonapp.query.queries.GetAccountOperationsQuery;
import org.example.eventsourcingcqrsspringaxonapp.query.queries.GetAllAccountsQuery;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/query/accounts")
@Slf4j
public class AccountQueryController {
    private final QueryGateway queryGateway;


    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }
    @GetMapping("/{id}")
    public CompletableFuture<BankAccountResponseDTO> getAccountById(@PathVariable String id) {
        return queryGateway.query(new GetAccountByIdQuery(id),  ResponseTypes.instanceOf(BankAccountResponseDTO.class)); // Placeholder for actual implementation
    }

    @GetMapping("/all")
    public CompletableFuture<List<BankAccountResponseDTO>> getAllAccounts() {
        return queryGateway.query(new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(BankAccountResponseDTO.class));
    }

    @GetMapping("/{id}/operations" )
    public CompletableFuture<List<AccountOperationResponseDTO>> getAccountOperations(@PathVariable String id) {
        return queryGateway.query(new GetAccountOperationsQuery(id), ResponseTypes.multipleInstancesOf(AccountOperationResponseDTO.class));
    }

}
