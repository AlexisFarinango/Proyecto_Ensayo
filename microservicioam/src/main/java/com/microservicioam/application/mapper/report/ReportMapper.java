package com.microservicioam.application.mapper.report;

import java.util.List;

import org.springframework.stereotype.Component;

import com.microservicioam.domain.dto.AccountReportDTO;
import com.microservicioam.domain.dto.MovementReportDTO;
import com.microservicioam.domain.models.Account;
import com.microservicioam.domain.models.Movement;

@Component
public class ReportMapper {
    public AccountReportDTO toAccountDTO(Account account, List<MovementReportDTO> movements) {
        return new AccountReportDTO(
                account.getNumber(),
                account.getType(),
                account.getAvailableBalance(),
                movements
        );
    }

    public MovementReportDTO toMovementDTO(Movement movement) {
        return new MovementReportDTO(
                movement.getMovementDate(),
                movement.getType(),
                movement.getValue(),
                movement.getBalance()
        );
    }

}
