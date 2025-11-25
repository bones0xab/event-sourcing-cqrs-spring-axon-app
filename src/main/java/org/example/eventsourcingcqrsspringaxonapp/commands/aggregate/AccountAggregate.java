package org.example.eventsourcingcqrsspringaxonapp.commands.aggregate;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.eventsourcingcqrsspringaxonapp.commands.enums.AccountStatus;

@Aggregate
@Slf4j
@Getter
@Setter
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info("Handling AddAccountCommand for account id: {}", command.getId());
        this.accountId = command.getId();
        this.balance = command.getInitialBalance();
        this.currency = command.getCurrency();
        this.status = AccountStatus.CREATED;
        log.info("Account created with id: {}, balance: {}, currency: {}, status: {}", accountId, balance, currency, status);

    }

}
