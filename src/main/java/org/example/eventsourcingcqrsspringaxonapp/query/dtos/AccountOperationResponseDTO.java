package org.example.eventsourcingcqrsspringaxonapp.query.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.eventsourcingcqrsspringaxonapp.commons.enums.OperationType;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperationResponseDTO {
    private Long id;
    private Date operationDate;
    private double amount; // ou BigDecimal
    private OperationType type;
}