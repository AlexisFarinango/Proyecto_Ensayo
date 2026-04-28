package com.retoavanzado.domain.models;

import lombok.Getter;

@Getter

public class Person {
    protected String name;
    protected String gender;
    protected String identification;
    protected String address;
    protected String phone;

    protected Person(String name, String gender, String identification, String address, String phone) {
        changeName(name);
        changeGender(gender);
        changeIdentification(identification);
        changeAddress(address);
        changePhone(phone);
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void changeGender(String gender) {
        if (gender == null || gender.isBlank()) {
            throw new IllegalArgumentException("Gender cannot be null or empty");
        }
        this.gender = gender;
    }

    public void changeIdentification(String identification) {
        if (identification == null || identification.isBlank())
            throw new IllegalArgumentException("Identification cannot be null or empty");
        this.identification = identification;
    }

    public void changeAddress(String address) {
        if (address == null || address.isBlank())
            throw new IllegalArgumentException("Address cannot be null or empty");
        this.address = address;
    }

    public void changePhone(String phone) {
        if (phone == null || phone.isBlank())
            throw new IllegalArgumentException("Phone cannot be null or empty");
        this.phone = phone;
    }

}
