package com.microservicioam.domain.dto;

import java.math.BigDecimal;

import com.microservicioam.domain.models.types.AccountType;

public record CreateAccountDTO(
    String number,
    AccountType type,
    BigDecimal initialBalance,
    Boolean status,
    String customerIdentification
) {}
