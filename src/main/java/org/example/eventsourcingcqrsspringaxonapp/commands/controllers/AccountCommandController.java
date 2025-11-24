package org.example.eventsourcingcqrsspringaxonapp.commands.controllers;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.eventsourcingcqrsspringaxonapp.commands.dtos.AddNewAccountRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {

    private CommandGateway commandGateway;

    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
    @PostMapping("/add")
    public String addNewaccount(@RequestBody AddNewAccountRequestDto addNewAccountRequestDto) {
        commandGateway.send(addNewAccountRequestDto);
    };
}
