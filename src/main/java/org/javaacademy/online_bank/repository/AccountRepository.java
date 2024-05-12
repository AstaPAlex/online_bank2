package org.javaacademy.online_bank.repository;

import org.javaacademy.online_bank.entity.Account;
import org.javaacademy.online_bank.entity.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AccountRepository {
    private final Map<String, Account> accounts = new HashMap<>();

    public void addAccount(String numberAccount, User user) {
        if (accounts.containsKey(numberAccount)) {
            throw new RuntimeException("This account number already exists!");
        }
        accounts.put(numberAccount, new Account(numberAccount, user));
    }

    public Account findAccountByNumber(String numberAccount) {
        return Optional.ofNullable(accounts.get(numberAccount))
                .orElseThrow(() -> new RuntimeException("There is no such account number!"));
    }

    public Set<Account> getAllAccountsByUser(User user) {
        return accounts.values().stream()
                .filter(account -> Objects.equals(account.getUser(), user))
                .collect(Collectors.toSet());
    }

}
