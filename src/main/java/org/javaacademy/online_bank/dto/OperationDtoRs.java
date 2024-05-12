package org.javaacademy.online_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.javaacademy.online_bank.entity.Currency;
import org.javaacademy.online_bank.entity.TypeOperation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OperationDtoRs {
    private UUID uuid;
    private LocalDateTime dateTime;
    private String numberAccount;
    private TypeOperation type;
    private BigDecimal amount;
    private String description;
    private Currency currency;
}
