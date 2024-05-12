package org.javaacademy.online_bank.repository;

import org.javaacademy.online_bank.entity.Account;
import org.javaacademy.online_bank.entity.Operation;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OperationRepository {
    private final Map<UUID, Operation> operations = new HashMap<>();

    public TreeSet<Operation> getAllOperationsByNumberAccount(String numberAccount) {
        return operations.values().stream()
                .filter(operation -> Objects.equals(operation.getNumberAccount(), numberAccount))
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(Operation::getDateTime).reversed()))
                );
    }

    public void add(Operation operation) {
        operations.put(operation.getUuid(), operation);
    }

    public TreeSet<Operation> getAllOperationsByAccounts(Set<Account> accounts) {
        return accounts.stream()
                .map(Account::getNumber)
                .flatMap(number -> getAllOperationsByNumberAccount(number).stream())
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(Operation::getDateTime).reversed()))
                );
    }

}
