package com.retoavanzado.domain.models;

import lombok.Getter;


@Getter
public class Customer extends Person {
    private String password;
    private Boolean status;

    public Customer(String name, String gender, String identification, String address, String phone, String password, boolean status) {
        super(name, gender, identification, address, phone);
        changePassword(password);
        changeStatus(status);
    }

    public void changePassword(String password) {
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be null or empty");
        this.password = password;
    }

    public void changeStatus(Boolean status) {
        if (status == null)
            throw new IllegalArgumentException("Status cannot be null");
        this.status = status;
    }

}
