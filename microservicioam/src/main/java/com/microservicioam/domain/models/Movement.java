package com.microservicioam.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.microservicioam.domain.models.types.MovementType;
import com.microservicioam.domain.services.MovementCalculatorFactory;

import lombok.Getter;

@Getter
public class Movement {
    private final LocalDateTime movementDate;
    private final MovementType type;
    private final BigDecimal value;
    private final BigDecimal balance;
    private final String accountNumber;

    public Movement(LocalDateTime movementDate, MovementType type, BigDecimal value, BigDecimal balance, String accountNumber) {
        this.movementDate = validateDate(movementDate);
        this.type = validateMovementType(type);
        this.value = validateValue(value);
        this.balance = validateBalance(balance);
        this.accountNumber = validateAccountNumber(accountNumber);
    }

    public LocalDateTime validateDate(LocalDateTime movementDate){
        if(movementDate == null){
            throw new IllegalArgumentException("Movement date cannot be null");
        }
        return movementDate;
    }

    public MovementType validateMovementType(MovementType type){
        if(type == null){
            throw new IllegalArgumentException("Movement type cannot be null");
        }
        return type;
    }

    public BigDecimal validateValue(BigDecimal value){
        if(value.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Value must be greater than zero");
        }
        return value;
    }

    public BigDecimal validateBalance(BigDecimal balance){
        if(balance.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        return balance;
    }

    public String validateAccountNumber(String accountNumber){
        if(accountNumber == null || accountNumber.isBlank()){
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        return accountNumber;
    }

    public static Movement registerMovement(String accountNumber, MovementType type, BigDecimal value, BigDecimal currentBalance, MovementCalculatorFactory factory){
        if (factory == null) throw new IllegalArgumentException("Factory cannot be null");
        if(value == null || value.compareTo(BigDecimal.ZERO)<=0) throw new IllegalArgumentException("Value cannot be null or zero");
        if(currentBalance == null) throw new IllegalArgumentException("Current balance cannot be null");
        BigDecimal newBalance = factory.get(type).calculate(currentBalance, value);

        return new Movement(LocalDateTime.now(), type, value, newBalance, accountNumber);  
        
    }


}
