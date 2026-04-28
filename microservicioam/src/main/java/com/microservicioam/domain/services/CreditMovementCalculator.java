package com.microservicioam.domain.services;

import java.math.BigDecimal;

public class CreditMovementCalculator implements MovementCalculator{
    @Override
    public BigDecimal calculate(BigDecimal currentBalance, BigDecimal value){
        return currentBalance.add(value);
    }

}
