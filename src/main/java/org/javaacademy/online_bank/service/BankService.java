package org.javaacademy.online_bank.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.config.BankProperty;
import org.javaacademy.online_bank.dto.OperationRefillDtoRq;
import org.javaacademy.online_bank.entity.Currency;
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
    private final BankProperty bankProperty;
    private final AccountService accountService;
    private final UserService userService;
    private final OperationService operationService;
    private final TransfersToOtherBanksService transfersToOtherBanksService;
    private final CurrencyService currencyService;

    public void payment(String numberAccount, BigDecimal amount, String description,
                        String token, Currency currencyPayment) {
        User user = userService.findUser(token);
        if (!accountService.accountOwnedUser(user, numberAccount)) {
            throw new RuntimeException();
        }
        Currency currencyAccount = accountService.findAccountByNumber(numberAccount).getCurrency();
        BigDecimal conversionAmount = currencyService.conversion(amount, currencyPayment, currencyAccount);
        accountService.minusBalance(numberAccount, conversionAmount);
        operationService.addOperation(createOperation(numberAccount, TypeOperation.PAYMENT,
                conversionAmount, description, currencyAccount));
    }

    public void refill(String numberAccount, BigDecimal amount, String description, Currency currencyRefill) {
        Currency currencyAccount = accountService.findAccountByNumber(numberAccount).getCurrency();
        BigDecimal conversionAmount = currencyService.conversion(amount, currencyRefill, currencyAccount);
        accountService.plusBalance(numberAccount, conversionAmount);
        operationService.addOperation(createOperation(numberAccount, TypeOperation.REFILL, conversionAmount,
                description, currencyAccount));
    }

    public TreeSet<Operation> getHistory(String token) {
        User user = userService.findUser(token);
        return operationService.getAllOperationsByUser(user);
    }

    private Operation createOperation(String numberAccount, TypeOperation type,
                                      BigDecimal amount, String description, Currency currency) {
        return new Operation(
                UUID.randomUUID(),
                LocalDateTime.now(),
                numberAccount,
                type,
                amount,
                description,
                currency);
    }

    public String info() {
        return bankProperty.getName();
    }

    public void transferToOtherBank(String token, BigDecimal amount, String description,
                                    String numberAccountUser, String numberAccountToSend, Currency currencyTransfer) {
        User user = userService.findUser(token);
        BigDecimal balance = accountService.getBalanceAccountByUser(numberAccountUser, user);
        OperationRefillDtoRq reqBody = new OperationRefillDtoRq(
                amount,
                numberAccountToSend,
                "Из банка: %s, от %s, описание: %s".formatted(bankProperty.getName(), user.getFullName(), description),
                currencyTransfer.getCurrency());
        if (balance.compareTo(amount) >= 0) {
            transfersToOtherBanksService.transfersToOtherBank(reqBody);
            accountService.minusBalance(numberAccountUser, amount);
            operationService.addOperation(createOperation(
                    numberAccountUser,
                    TypeOperation.PAYMENT,
                    amount, description, currencyTransfer)
            );
        }
    }

    public void buyCurrency(String numberAccountFrom, String numberAccountTo, BigDecimal amount, String token) {
        User user = userService.findUser(token);
        if (!accountService.accountOwnedUser(user, numberAccountFrom)
                || !accountService.accountOwnedUser(user, numberAccountTo)) {
            throw new RuntimeException("The accounts do not belong to this user");
        }
/*        BigDecimal rate = currencyService.findRate(
                accountService.findAccountByNumber(numberAccountFrom).getCurrency(),
                accountService.findAccountByNumber(numberAccountTo).getCurrency()
        );*/
        payment(numberAccountFrom, amount, "Конвертация",
                token, accountService.findAccountByNumber(numberAccountFrom).getCurrency());
        refill(numberAccountTo, amount, "Конвертация",
                accountService.findAccountByNumber(numberAccountFrom).getCurrency());
    }

}
