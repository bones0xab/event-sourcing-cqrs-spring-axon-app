package org.example.eventsourcingcqrsspringaxonapp.commands.aggregate;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.eventsourcingcqrsspringaxonapp.commands.command.AddAccountCommand;
import org.example.eventsourcingcqrsspringaxonapp.commands.enums.AccountStatus;
import org.example.eventsourcingcqrsspringaxonapp.commands.events.AccountCreatedEvent;

@Aggregate
@Slf4j
@Getter
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {

        log.info("Account Aggregate Created");
    }

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info("CreateAccount Command Received");
        if (command.getInitialBalance()<0) throw  new IllegalArgumentException("Balance negative exception");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                command.getCurrency(),
                AccountStatus.CREATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.accountId();
        this.balance = event.initialBalance();
        this.currency = event.currency();
        this.status = event.accountStatus();
        log.info("AccountCreatedEvent Applied");
    }

}
