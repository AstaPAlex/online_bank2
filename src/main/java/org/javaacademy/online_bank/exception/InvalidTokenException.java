package org.javaacademy.online_bank.exception;

import lombok.experimental.StandardException;

@StandardException
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Неверный токен!");
    }
}
