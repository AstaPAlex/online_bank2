package org.javaacademy.online_bank.exception;

import lombok.experimental.StandardException;

@StandardException
public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException() {
        super("Пользователь не найден!");
    }
}
