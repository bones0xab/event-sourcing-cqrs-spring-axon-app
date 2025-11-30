package org.example.eventsourcingcqrsspringaxonapp.commands.events;

import org.example.eventsourcingcqrsspringaxonapp.commons.enums.AccountStatus;

public record AccountCreatedEvent(String accountId, double initialBalance, String currency, AccountStatus accountStatus) {
}
