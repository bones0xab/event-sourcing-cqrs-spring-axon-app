package org.example.eventsourcingcqrsspringaxonapp.query.repository;

import org.example.eventsourcingcqrsspringaxonapp.query.dtos.AccountOperationResponseDTO;
import org.example.eventsourcingcqrsspringaxonapp.query.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountOperationRepo extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByBankAccountId(String s);
}
