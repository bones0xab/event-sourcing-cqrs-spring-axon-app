package org.example.eventsourcingcqrsspringaxonapp.commands.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;


public record AddAccountCommand (@TargetAggregateIdentifier String accountId, double initialBalance,String currency) {
}
