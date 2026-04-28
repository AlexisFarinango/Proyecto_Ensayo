package com.microservicioam.application.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String number){
        super("Account "+ number +" not found.");
    }

}
