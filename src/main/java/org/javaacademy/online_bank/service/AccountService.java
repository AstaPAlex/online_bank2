package org.javaacademy.online_bank.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.dto.AccountDto;
import org.javaacademy.online_bank.entity.Account;
import org.javaacademy.online_bank.entity.User;
import org.javaacademy.online_bank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {
    private static final Integer MAX_COUNT_ACCOUNT = 999_999;
    private Integer countAccount = 0;
    private final AccountRepository accountRepository;

    private String generateNumberAccount() {
        if (countAccount < MAX_COUNT_ACCOUNT) {
            countAccount++;
            return String.format("%06d", countAccount);
        }
        throw new RuntimeException("The number of accounts has been exceeded!");
    }

    public String createAccountForUser(User user) {
        String accountNumber = generateNumberAccount();
        accountRepository.addAccount(accountNumber, user);
        return accountNumber;
    }

    public void plusBalance(String numberAccount, BigDecimal amount) {
        Account account = accountRepository.findAccountByNumber(numberAccount);
        account.setBalance(getBalance(numberAccount).add(amount));
    }

    public void minusBalance(String numberAccount, BigDecimal amount) {
        Account account = accountRepository.findAccountByNumber(numberAccount);
        BigDecimal balance = getBalance(numberAccount);
        if (balance.compareTo(amount) < 0) {
            throw new RuntimeException("There are not enough funds in the account!");
        }
        account.setBalance(balance.subtract(amount));
    }

    public Set<Account> findAllAccountsByUser(User user) {
        return accountRepository.getAllAccountsByUser(user);
    }

    public List<AccountDto> getAllAccountsByUser(User user) {
        return findAllAccountsByUser(user).stream()
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

    public BigDecimal getBalance(String numberAccount) {
        return accountRepository.findAccountByNumber(numberAccount).getBalance();
    }

    public boolean accountOwnedUser(User user, String numberAccount) {
        User userOwned = accountRepository.findAccountByNumber(numberAccount).getUser();
        return Objects.equals(userOwned, user);
    }

    public BigDecimal getBalanceAccountByUser(String numberAccount, User user) {
        if (accountOwnedUser(user, numberAccount)) {
            return getBalance(numberAccount);
        }
        throw new RuntimeException("The account does not belong to this user!");
    }
}
