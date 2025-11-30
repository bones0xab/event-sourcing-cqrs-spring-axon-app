package org.example.eventsourcingcqrsspringaxonapp.query.mapper;

import org.example.eventsourcingcqrsspringaxonapp.query.dtos.AccountOperationResponseDTO;
import org.example.eventsourcingcqrsspringaxonapp.query.dtos.BankAccountResponseDTO;
import org.example.eventsourcingcqrsspringaxonapp.query.entities.AccountOperation;
import org.example.eventsourcingcqrsspringaxonapp.query.entities.BankAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankMapper {
    BankAccountResponseDTO fromBankAccount(BankAccount bankAccount);
    AccountOperationResponseDTO fromAccountOperation(AccountOperation accountOperation);
}
