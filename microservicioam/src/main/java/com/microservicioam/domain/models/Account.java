package com.microservicioam.domain.models;

import java.math.BigDecimal;

import com.microservicioam.domain.models.types.AccountType;

import lombok.Getter;

@Getter
public class Account {
    private final String number;
    private final AccountType type;
    private final BigDecimal initialBalance;
    private BigDecimal availableBalance;
    private Boolean status;
    private final String customerIdentification;

    public Account (String number, AccountType type, BigDecimal initialBalance, BigDecimal availableBalance, Boolean status, String customerIdentification) {
        this.number = validateNumber(number);
        this.type = validateAccountType(type);
        this.initialBalance = validateInitialBalance(initialBalance);
        if (availableBalance == null) {
            this.availableBalance = this.initialBalance;
        } else {
            this.availableBalance = validateAvailableBalance(availableBalance);
        }
        changeStatus(status);
        this.customerIdentification = validateCustomerIdentification(customerIdentification);
    }

    public String validateNumber(String number){
        if(number == null || number.isBlank()){
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        return number;
    }

    public AccountType validateAccountType(AccountType type){
        if(type == null || type.toString().isBlank()){
            throw new IllegalArgumentException("Account type cannot be null");
        }
        return type;
    }

    public BigDecimal validateInitialBalance(BigDecimal initialBalance){
        if(initialBalance.compareTo(BigDecimal.ZERO) < 0 ){
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        return initialBalance;
    }

    
    public String validateCustomerIdentification(String customerIdentification){
        if(customerIdentification == null || customerIdentification.isBlank()){
            throw new IllegalArgumentException("Customer identification cannot be null or empty");
        }
        return customerIdentification;
    }
    
    private BigDecimal validateAvailableBalance(BigDecimal availableBalance) {
        if (availableBalance == null) {
            throw new IllegalArgumentException("Available balance cannot be null");
        }
        if (availableBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Available balance cannot be negative");
        }
        return availableBalance;
    }
    
    public void changeStatus(Boolean status){
        if(status == null){
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }
    
    public void changeAvailableBalance(BigDecimal newBalance) {
        this.availableBalance = validateAvailableBalance(newBalance);
    }
    
}
