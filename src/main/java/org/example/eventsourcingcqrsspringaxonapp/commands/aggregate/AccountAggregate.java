package org.example.eventsourcingcqrsspringaxonapp.commands.aggregate;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.eventsourcingcqrsspringaxonapp.commands.command.AddAccountCommand;
import org.example.eventsourcingcqrsspringaxonapp.commands.command.CreditAccountCommand;
import org.example.eventsourcingcqrsspringaxonapp.commands.command.DebitAccountCommand;
import org.example.eventsourcingcqrsspringaxonapp.commands.enums.AccountStatus;
import org.example.eventsourcingcqrsspringaxonapp.commands.events.AccountCreatedEvent;
import org.example.eventsourcingcqrsspringaxonapp.commands.events.AccountCreditedEvent;
import org.example.eventsourcingcqrsspringaxonapp.commands.events.AccountDebitEvent;

@Aggregate
@Slf4j
@Getter
@NoArgsConstructor
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;


    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info("CreateAccount Command Received");
        if (command.initialBalance()<0) throw  new IllegalArgumentException("Balance negative exception");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.id(),
                command.initialBalance(),
                command.currency(),
                AccountStatus.CREATED
        ));
    }

    @CommandHandler
    public AccountAggregate(CreditAccountCommand command){
        log.info("CreditAccount Command Received");
        if (command.amount() <= 0)
            throw new IllegalArgumentException("Amount must be positive");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.accountId(),
                command.amount(),
                command.currency()
        ));
    }

    @CommandHandler
    public AccountAggregate(DebitAccountCommand command){
        log.info("DebitCommand Command Received");
        if (command.amount() <= 0)
            throw new IllegalArgumentException("Amount must be positive");
        AggregateLifecycle.apply(new AccountDebitEvent(
                command.accountId(),
                command.amount(),
                command.currency()
        ));
    }

    @EventSourcingHandler
    public void handleAccountDebitedEvent(AccountDebitEvent event) {
        this.accountId = event.accountId();
        this.balance = event.amount();
        this.currency = event.currency();
        log.info("AccountDebitEvent Applied");

    }



    @EventSourcingHandler
    public void handleAccountCreditedEvent(AccountCreditedEvent event) {
        this.accountId = event.accountId();
        this.balance = event.amount();
        this.currency = event.currency();
        log.info("AccountCreditedEvent Applied");

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
