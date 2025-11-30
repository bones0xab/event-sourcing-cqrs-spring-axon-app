package org.example.eventsourcingcqrsspringaxonapp.query.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.eventsourcingcqrsspringaxonapp.commons.enums.AccountStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountResponseDTO {
    private String id;
    private double balance; // ou BigDecimal
    private String currency;
    private AccountStatus status;
}