package com.microservicioam.domain.dto;

import java.math.BigDecimal;

import com.microservicioam.domain.models.types.MovementType;

public record CreateMovementDTO(
    String accountNumber,
    MovementType type,
    BigDecimal value
) {}
