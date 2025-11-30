package org.example.eventsourcingcqrsspringaxonapp.query.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.eventsourcingcqrsspringaxonapp.commons.enums.AccountStatus;

import java.util.List;

@Entity
@Getter
@Setter
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    @Id
    private String id;

    private double balance;

    private String currency;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY)
    private List<AccountOperation> accountOperations;
}
