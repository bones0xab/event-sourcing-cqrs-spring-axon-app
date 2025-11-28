package org.example.eventsourcingcqrsspringaxonapp.commands.dtos;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record DebitAccountDto (String accountId, double amount, String currency) {
}
