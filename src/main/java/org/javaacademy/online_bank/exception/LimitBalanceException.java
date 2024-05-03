package org.javaacademy.online_bank.exception;

import lombok.experimental.StandardException;

@StandardException
public class LimitBalanceException extends RuntimeException {
    public LimitBalanceException() {
        super("Лимита не балансе не достаточно для операции!");
    }
}
