package com.microservicioam.application.exception;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String number) {
        super("Account with number " + number + " already exists.");
    }
}
