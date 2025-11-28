package org.example.eventsourcingcqrsspringaxonapp.commands.events;

public record AccountDebitEvent(String accountId, double amount, String currency) {
}
