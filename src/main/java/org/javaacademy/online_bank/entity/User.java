package org.javaacademy.online_bank.entity;

import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class User {
    @NonNull
    private final String fullName;
    @NonNull
    private final String numberPhone;
    private UUID uuid;

}
