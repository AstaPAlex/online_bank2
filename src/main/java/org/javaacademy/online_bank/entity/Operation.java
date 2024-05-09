package org.javaacademy.online_bank.entity;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Operation {
    @NonNull
    private UUID uuid;
    @NonNull
    private LocalDateTime dateTime;
    @NonNull
    private String numberAccount;
    @NonNull
    private TypeOperation type;
    @NonNull
    private BigDecimal amount;
    @NonNull
    private String description;

}
