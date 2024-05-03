package org.javaacademy.online_bank.exception;

import lombok.experimental.StandardException;

@StandardException
public class AlreadyExistsUserException extends RuntimeException {
    public AlreadyExistsUserException() {
        super("Пользователь с таким номером уже существует!");
    }
}
