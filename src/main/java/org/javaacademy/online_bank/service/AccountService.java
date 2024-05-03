package org.javaacademy.online_bank.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.dto.AccountDto;
import org.javaacademy.online_bank.entity.Account;
import org.javaacademy.online_bank.entity.User;
import org.javaacademy.online_bank.exception.LimitBalanceException;
import org.javaacademy.online_bank.exception.LimitCountAccountException;
import org.javaacademy.online_bank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {
    private Integer countNumber = 0;
    private static final Integer MAX_COUNT_NUMBER = 999_999;
    private final AccountRepository accountRepository;

    private String generateAccount() {
        if (countNumber < MAX_COUNT_NUMBER) {
            countNumber++;
            return String.format("%06d", countNumber);
        }
        throw new LimitCountAccountException();
    }

    public void createAccountForUser(User user) {
        accountRepository.addAccount(generateAccount(), user);
    }

    public void plusBalance(String number, BigDecimal amount) {
        Account account = accountRepository.findAccountByNumber(number);
        account.setBalance(getBalance(number).add(amount));
    }

    public void minusBalance(String number, BigDecimal amount) {
        Account account = accountRepository.findAccountByNumber(number);
        BigDecimal balance = getBalance(number);
        if (balance.compareTo(amount) < 0) {
            throw new LimitBalanceException();
        }
        account.setBalance(balance.subtract(amount));
    }

    public List<AccountDto> findAllAccountByUser(User user) {
        return accountRepository.getAllAccountByUser(user).stream()
                .map(this::convertToDto)
                .toList();
    }

    private AccountDto convertToDto(Account account) {
        return new AccountDto(
                account.getNumber(),
                account.getUser(),
                account.getBalance()
        );
    }

    public BigDecimal getBalance(String number) {
        return accountRepository.findAccountByNumber(number).getBalance();
    }

    public boolean accountOwnedUser(User user, String number) {
        User userOwned = accountRepository.findAccountByNumber(number).getUser();
        return Objects.equals(userOwned, user);
    }
}
