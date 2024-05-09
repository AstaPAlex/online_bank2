package org.javaacademy.online_bank.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.entity.Operation;
import org.javaacademy.online_bank.entity.TypeOperation;
import org.javaacademy.online_bank.entity.User;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.TreeSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankService {
    private final AccountService accountService;
    private final UserService userService;
    private final OperationService operationService;

    public void payment(String numberAccount, BigDecimal amount, String description, String token) {
        User user = userService.findUser(token);
        if (!accountService.accountOwnedUser(user, numberAccount)) {
            throw new RuntimeException();
        }
        accountService.minusBalance(numberAccount, amount);
        operationService.addOperation(createOperation(numberAccount, TypeOperation.PAYMENT, amount, description));
    }

    public void refill(String numberAccount, BigDecimal amount, String description) {
        accountService.plusBalance(numberAccount, amount);
        operationService.addOperation(createOperation(numberAccount, TypeOperation.REFILL, amount, description));
    }

    public TreeSet<Operation> getHistory(String token) {
        User user = userService.findUser(token);
        return operationService.getAllOperationsByUser(user);
    }

    private Operation createOperation(String numberAccount, TypeOperation type,
                                      BigDecimal amount, String description) {
        return new Operation(
                UUID.randomUUID(),
                LocalDateTime.now(),
                numberAccount,
                type,
                amount,
                description);
    }

}
