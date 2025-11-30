package org.example.eventsourcingcqrsspringaxonapp.query.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.eventsourcingcqrsspringaxonapp.commons.enums.OperationType;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date operationDate;

    private double amount; // Ou BigDecimal

    @Enumerated(EnumType.STRING)
    private OperationType type; // DEBIT ou CREDIT

    @ManyToOne // Plusieurs opérations pour un seul compte
    private BankAccount bankAccount;
}
