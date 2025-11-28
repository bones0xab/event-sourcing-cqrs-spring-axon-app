package org.example.eventsourcingcqrsspringaxonapp.commands.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record DebitAccountCommand(@TargetAggregateIdentifier String accountId, double amount, String currency) {
}
