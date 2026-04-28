package com.microservicioam.application.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String id) {
        super("Customer with identification " + id + " does not exist");
    }

}
