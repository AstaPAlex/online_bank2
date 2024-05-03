package org.javaacademy.online_bank.exception;

import lombok.experimental.StandardException;

@StandardException
public class NotFoundAccountException extends RuntimeException {
    public NotFoundAccountException() {
        super("Счет не найден!");
    }
}
