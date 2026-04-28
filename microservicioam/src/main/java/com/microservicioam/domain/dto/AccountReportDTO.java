package com.microservicioam.domain.dto;

import java.math.BigDecimal;
import java.util.List;

import com.microservicioam.domain.models.types.AccountType;

public record AccountReportDTO(
    String number,
    AccountType type,
    BigDecimal availableBalance,
    List<MovementReportDTO> movements
) {}
