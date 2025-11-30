package org.example.eventsourcingcqrsspringaxonapp.query.handlers;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.example.eventsourcingcqrsspringaxonapp.commands.events.AccountCreatedEvent;
import org.example.eventsourcingcqrsspringaxonapp.commands.events.AccountCreditedEvent;
import org.example.eventsourcingcqrsspringaxonapp.commands.events.AccountDebitEvent;
import org.example.eventsourcingcqrsspringaxonapp.commons.enums.OperationType;
import org.example.eventsourcingcqrsspringaxonapp.query.dtos.AccountOperationResponseDTO;
import org.example.eventsourcingcqrsspringaxonapp.query.dtos.BankAccountResponseDTO;
import org.example.eventsourcingcqrsspringaxonapp.query.entities.AccountOperation;
import org.example.eventsourcingcqrsspringaxonapp.query.mapper.BankMapper;
import org.example.eventsourcingcqrsspringaxonapp.query.queries.GetAccountByIdQuery;
import org.example.eventsourcingcqrsspringaxonapp.query.queries.GetAccountOperationsQuery;
import org.example.eventsourcingcqrsspringaxonapp.query.queries.GetAllAccountsQuery;
import org.example.eventsourcingcqrsspringaxonapp.query.repository.AccountOperationRepo;
import org.example.eventsourcingcqrsspringaxonapp.query.repository.BankRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.eventsourcingcqrsspringaxonapp.query.entities.BankAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AccountQueryHandler {
    private final BankRepository bankRepository;
    private final AccountOperationRepo accountOperationRepo;
    private final BankMapper bankMapper;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(event.accountId());
        bankAccount.setBalance(event.initialBalance());
        bankAccount.setCurrency(event.currency());
        bankAccount.setStatus(event.accountStatus());
        bankRepository.save(bankAccount);
        log.info("BankAccount created with ID: " + bankAccount.getId());
    }

    @EventHandler
    public void on(AccountCreditedEvent event){
        BankAccount bankAccount = bankRepository.findById(event.accountId()).get();
        BigDecimal balance = BigDecimal.valueOf(event.amount());
        bankAccount.setBalance(bankAccount.getBalance() + balance.doubleValue());
        BankAccount savedAccount = bankRepository.save(bankAccount);

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationDate(new Date());
        accountOperation.setAmount(event.amount());
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setBankAccount(savedAccount);

        accountOperationRepo.save(accountOperation);

        log.info("Account Credited with ID: " + accountOperation.getId());
    }

    @EventHandler
    public void on(AccountDebitEvent event){
        BankAccount bankAccount = bankRepository.findById(event.accountId()).get();
        BigDecimal balance = BigDecimal.valueOf(event.amount());
        bankAccount.setBalance(bankAccount.getBalance() - balance.doubleValue());
        BankAccount savedAccount = bankRepository.save(bankAccount);

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationDate(new Date());
        accountOperation.setAmount(event.amount());
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setBankAccount(savedAccount);

        accountOperationRepo.save(accountOperation);
        log.info("Account Debited with ID: " + accountOperation.getId());
    }


    @QueryHandler
    public BankAccountResponseDTO on(GetAccountByIdQuery query) {
        BankAccount bankAccount = bankRepository.findById(query.id()).get();
        return bankMapper.fromBankAccount(bankAccount);
    }

    @QueryHandler
    public List<BankAccountResponseDTO> on(GetAllAccountsQuery query) {
        List<BankAccount> account = bankRepository.findAll();
        System.out.println("Fetching all accounts, total count: " + account);
        List<BankAccountResponseDTO> dtos = account.stream().map(bankAccount -> bankMapper.fromBankAccount(bankAccount)).collect(Collectors.toList());
        System.out.println("FETCHING THE TRANSLATION DTOS : " + dtos);
        return dtos;
    }

    @QueryHandler
    public List<AccountOperationResponseDTO> on (GetAccountOperationsQuery query) {
        List<AccountOperation> accounts = accountOperationRepo.findByBankAccountId(query.accountId());
        return accounts.stream()
                .map(bankMapper::fromAccountOperation)
                .collect(Collectors.toList());
    }
}
