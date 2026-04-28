package com.microservicioam.domain.dto;

import java.time.LocalDate;
import java.util.List;

public record ReportResponseDTO(
    String clientId,
    LocalDate startDate,
    LocalDate endDate,
    List<AccountReportDTO> accounts
) {}
