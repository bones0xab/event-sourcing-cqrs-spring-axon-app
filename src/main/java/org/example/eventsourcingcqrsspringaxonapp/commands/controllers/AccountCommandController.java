package org.example.eventsourcingcqrsspringaxonapp.commands.controllers;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.eventsourcingcqrsspringaxonapp.commands.command.AddAccountCommand;
import org.example.eventsourcingcqrsspringaxonapp.commands.command.CreditAccountCommand;
import org.example.eventsourcingcqrsspringaxonapp.commands.command.DebitAccountCommand;
import org.example.eventsourcingcqrsspringaxonapp.commons.dtos.AddNewAccountRequestDto;
import org.example.eventsourcingcqrsspringaxonapp.commons.dtos.CreditAccountDTO;
import org.example.eventsourcingcqrsspringaxonapp.commons.dtos.DebitAccountDto;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/accounts")
@AllArgsConstructor
public class AccountCommandController {

    private  CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping("/add")
    public CompletableFuture<String> addNewaccount(@RequestBody AddNewAccountRequestDto requestDto) {
        CompletableFuture<String> reponse = commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                requestDto.initialeBalance(),
                requestDto.currency()
        ));
        return reponse;
    };

    @PutMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountDto debitAccountDto) {
        CompletableFuture<String> response = commandGateway.send(new DebitAccountCommand(
                debitAccountDto.accountId(),
                debitAccountDto.amount(),
                debitAccountDto.currency()
        ));
        return response;
    }
    @PutMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountDTO creditAccountDto) {
        CompletableFuture<String> response = commandGateway.send(new CreditAccountCommand(
                creditAccountDto.accountId(),
                creditAccountDto.amount(),
                creditAccountDto.currency()
                )
        );

        return response;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception) {
        return exception.getMessage();
    }
}
