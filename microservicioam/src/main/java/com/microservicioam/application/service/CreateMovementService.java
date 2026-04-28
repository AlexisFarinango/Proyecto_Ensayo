package com.microservicioam.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.microservicioam.application.exception.AccountNotFoundException;
import com.microservicioam.application.exception.BadRequestException;
import com.microservicioam.application.exception.InsufficientBalanceException;
import com.microservicioam.application.port.in.CreateMovementPort;
import com.microservicioam.application.port.out.AccountRepositoryPort;
import com.microservicioam.application.port.out.MovementRepositoryPort;
import com.microservicioam.application.service.template.AbstractMovementUseCase;
import com.microservicioam.domain.dto.CreateMovementDTO;
import com.microservicioam.domain.models.Account;
import com.microservicioam.domain.models.Movement;
import com.microservicioam.domain.services.MovementCalculatorFactory;

import reactor.core.publisher.Mono;

@Service
public class CreateMovementService extends AbstractMovementUseCase implements CreateMovementPort {
    private final MovementRepositoryPort movementRepository;
    private final AccountRepositoryPort accountRepository;
    private final MovementCalculatorFactory calculatorFactory;

    private final Logger log = LoggerFactory.getLogger(CreateMovementService.class);

    public CreateMovementService(MovementRepositoryPort movementRepository, AccountRepositoryPort accountRepository, MovementCalculatorFactory calculatorFactory) {
        this.movementRepository = movementRepository;
        this.accountRepository = accountRepository;
        this.calculatorFactory = calculatorFactory;
    }

    @Override
    public Mono<Void> createMovement(CreateMovementDTO createMovementDTO) {
        return execute(createMovementDTO);
    }

    @Override
    public Mono<Account> findAccount(String accountNumber) {
        return accountRepository.findAccountById(accountNumber)
                .switchIfEmpty(Mono.error(new AccountNotFoundException(accountNumber)));
    }

    @Override
    protected Mono<Void> applyAndPersist(Account account, CreateMovementDTO dto) {
        Movement movement;
        try {
            movement = Movement.registerMovement(
                    account.getNumber(),
                    dto.type(),
                    dto.value(),
                    account.getAvailableBalance(),
                    calculatorFactory
            );
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("insufficient")) {
                return Mono.error(new InsufficientBalanceException());
            }
            return Mono.error(new BadRequestException(e.getMessage()));
        }

        account.changeAvailableBalance(movement.getBalance());

        return accountRepository.saveAccount(account)
                .then(movementRepository.saveMovement(movement))
                .doOnSuccess(v -> log.info("Movement of type {} for account {} with value {} applied successfully.",
                        dto.type(), account.getNumber(), dto.value()));
    }
}
