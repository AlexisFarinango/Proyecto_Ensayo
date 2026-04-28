package com.microservicioam.domain.services;

import java.math.BigDecimal;

public class DebitMovementCalculator implements MovementCalculator{
    @Override
    public BigDecimal calculate(BigDecimal currentBalance, BigDecimal value){
        BigDecimal newBalance = currentBalance.subtract(value);
        if(newBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Insufficient funds for this debit movement");
        }
        return newBalance;
    }
}
