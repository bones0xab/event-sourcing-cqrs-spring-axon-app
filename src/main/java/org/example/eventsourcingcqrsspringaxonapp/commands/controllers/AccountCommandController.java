package org.example.eventsourcingcqrsspringaxonapp.commands.controllers;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.eventsourcingcqrsspringaxonapp.commands.aggregate.AccountAggregate;
import org.example.eventsourcingcqrsspringaxonapp.commands.command.AddAccountCommand;
import org.example.eventsourcingcqrsspringaxonapp.commands.dtos.AddNewAccountRequestDto;
import org.springframework.beans.factory.BeanRegistrarDslMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {
    private  CommandGateway commandGateway;
    private EventStore eventStore;
    private AccountAggregate accountAggregate;

    public AccountCommandController(CommandGateway commandGateway, EventStore eventStore, AccountAggregate accountAggregate) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
        this.accountAggregate = accountAggregate;
    }

    @PostMapping("/add")
    public CompletableFuture<String> addNewaccount(@RequestBody AddNewAccountRequestDto requestDto) {
        CompletableFuture<String> reponse = commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                requestDto.initialeBalance(),
                requestDto.currency()
        ),null, String.class);
        return reponse;
    };

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception) {
        return exception.getMessage();
    }
}
