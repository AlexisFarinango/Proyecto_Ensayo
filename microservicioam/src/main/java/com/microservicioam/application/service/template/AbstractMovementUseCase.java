package com.microservicioam.application.service.template;

import java.math.BigDecimal;

import com.microservicioam.application.exception.AccountNotFoundException;
import com.microservicioam.domain.dto.CreateMovementDTO;
import com.microservicioam.domain.models.Account;

import reactor.core.publisher.Mono;

public abstract class AbstractMovementUseCase {

    public Mono<Void> execute(CreateMovementDTO movementDTO){
        return validateInput(movementDTO)
                .then(findAccount(movementDTO.accountNumber()))
                .flatMap(account -> applyAndPersist(account, movementDTO));
    }

    protected Mono<Void> validateInput(CreateMovementDTO movementDTO){
        if (movementDTO == null) return Mono.error(new IllegalArgumentException("Body cannot be null"));
        if (movementDTO.accountNumber() == null || movementDTO.accountNumber().isBlank()) return Mono.error(new IllegalArgumentException("Account number cannot be null or blank"));
        if (movementDTO.type() == null) return Mono.error(new IllegalArgumentException("Movement type cannot be null"));
        if (movementDTO.value() == null || movementDTO.value().compareTo(BigDecimal.ZERO) <= 0)return Mono.error(new IllegalArgumentException("Value must be greater than zero"));

        return Mono.empty();

    }

    protected abstract Mono<Account> findAccount(String accountNumber);

    protected abstract Mono<Void> applyAndPersist(Account account, CreateMovementDTO movementDTO);

    protected Mono<Account> notFound(String accountNumber){
        return Mono.error(new AccountNotFoundException(accountNumber));
    }

}
