package com.microservicioam.application.exception;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException() {
        super("Insufficient funds for account");
    }

}
