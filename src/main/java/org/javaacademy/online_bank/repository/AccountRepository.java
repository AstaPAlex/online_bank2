package org.javaacademy.online_bank.repository;

import org.javaacademy.online_bank.entity.Account;
import org.javaacademy.online_bank.entity.User;
import org.javaacademy.online_bank.exception.AlreadyExistsAccountException;
import org.javaacademy.online_bank.exception.NotFoundAccountException;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class AccountRepository {
    private final Map<String, Account> accounts = new HashMap<>();

    public void addAccount(String number, User user) {
        if (accounts.containsKey(number)) {
            throw new AlreadyExistsAccountException();
        }
        accounts.put(number, new Account(number, user));
    }

    public Account findAccountByNumber(String number) {
        return Optional.ofNullable(accounts.get(number)).orElseThrow(NotFoundAccountException::new);
    }

    public List<Account> getAllAccountByUser(User user) {
        return accounts.values().stream()
                .filter(account -> Objects.equals(account.getUser(), user))
                .toList();
    }

}
