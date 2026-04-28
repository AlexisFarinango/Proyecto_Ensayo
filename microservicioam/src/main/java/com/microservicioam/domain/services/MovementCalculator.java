package com.microservicioam.domain.services;

import java.math.BigDecimal;

public interface MovementCalculator {
    BigDecimal calculate(BigDecimal currentBalance, BigDecimal value);
}
