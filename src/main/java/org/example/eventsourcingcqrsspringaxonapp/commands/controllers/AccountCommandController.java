package org.example.eventsourcingcqrsspringaxonapp.commands.controllers;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.eventsourcingcqrsspringaxonapp.commands.aggregate.AccountAggregate;
import org.example.eventsourcingcqrsspringaxonapp.commands.command.AddAccountCommand;
import org.example.eventsourcingcqrsspringaxonapp.commands.command.CreditAccountCommand;
import org.example.eventsourcingcqrsspringaxonapp.commands.command.DebitAccountCommand;
import org.example.eventsourcingcqrsspringaxonapp.commands.dtos.AddNewAccountRequestDto;
import org.example.eventsourcingcqrsspringaxonapp.commands.dtos.CreditAccountDTO;
import org.example.eventsourcingcqrsspringaxonapp.commands.dtos.DebitAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountDto debitAccountDto) {
        CompletableFuture<String> response = commandGateway.send(new DebitAccountCommand(
                debitAccountDto.accountId(),
                debitAccountDto.amount(),
                debitAccountDto.currency()
        ));
        return response;
    }
    @PostMapping("/credit")
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
