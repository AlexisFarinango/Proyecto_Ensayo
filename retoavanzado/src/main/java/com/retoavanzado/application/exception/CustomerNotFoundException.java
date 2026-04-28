package com.retoavanzado.application.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String identification) {
        super("Customer with identification " + identification + " not found.");
    }
}