package org.javaacademy.online_bank.repository;

import org.javaacademy.online_bank.entity.Account;
import org.javaacademy.online_bank.entity.Currency;
import org.javaacademy.online_bank.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccountRepository {
    private final Map<String, Account> accounts = new HashMap<>();

    public void addAccount(String numberAccount, User user, Currency currency) {
        if (accounts.containsKey(numberAccount)) {
            throw new RuntimeException("This account number already exists!");
        }
        accounts.put(numberAccount, new Account(numberAccount, user, currency));
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
