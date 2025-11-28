package org.example.eventsourcingcqrsspringaxonapp.commands.events;

public record AccountCreditedEvent(String accountId, double amount, String currency) {
}
