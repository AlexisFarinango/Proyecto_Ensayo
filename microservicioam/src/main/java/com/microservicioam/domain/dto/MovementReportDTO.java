package com.microservicioam.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.microservicioam.domain.models.types.MovementType;

public record MovementReportDTO(
    LocalDateTime movement_date,
    MovementType type,
    BigDecimal value,
    BigDecimal balance
) {}
