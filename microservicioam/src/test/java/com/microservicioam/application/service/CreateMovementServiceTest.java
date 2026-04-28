package com.microservicioam.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicioam.application.exception.AccountNotFoundException;
import com.microservicioam.application.exception.InsufficientBalanceException;
import com.microservicioam.application.port.out.AccountRepositoryPort;
import com.microservicioam.application.port.out.MovementRepositoryPort;
import com.microservicioam.domain.dto.CreateMovementDTO;
import com.microservicioam.domain.models.Account;
import com.microservicioam.domain.models.Movement;
import com.microservicioam.domain.models.types.AccountType;
import com.microservicioam.domain.models.types.MovementType;
import com.microservicioam.domain.services.MovementCalculatorFactory;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CreateMovementServiceTest {

    @Mock
    private MovementRepositoryPort movementRepository;

    @Mock
    private AccountRepositoryPort accountRepository;

    private MovementCalculatorFactory factory;
    private CreateMovementService service;

    @BeforeEach
    void setUp() {
        factory = new MovementCalculatorFactory();
        service = new CreateMovementService(movementRepository, accountRepository, factory);

        when(accountRepository.findAccountById(any())).thenReturn(Mono.empty());
    }

    @Test
    void shouldCreateDebitMovementAndUpdateAvailableBalance() {
        Account account = new Account(
                "123",
                AccountType.SAVINGS,
                new BigDecimal("10.00"),
                new BigDecimal("10.00"),
                true,
                "1723456781"
        );

        CreateMovementDTO dto = new CreateMovementDTO(
                "123",
                MovementType.DEBIT,
                new BigDecimal("5.00")
        );

        when(accountRepository.findAccountById("123")).thenReturn(Mono.just(account));
        when(accountRepository.saveAccount(any(Account.class))).thenReturn(Mono.empty());
        when(movementRepository.saveMovement(any(Movement.class))).thenReturn(Mono.empty());

        StepVerifier.create(service.createMovement(dto))
                .verifyComplete();

        InOrder inOrder = inOrder(accountRepository, movementRepository);
        inOrder.verify(accountRepository).saveAccount(any(Account.class));
        inOrder.verify(movementRepository).saveMovement(any(Movement.class));

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).saveAccount(accountCaptor.capture());

        org.junit.jupiter.api.Assertions.assertEquals(
                new BigDecimal("5.00"),
                accountCaptor.getValue().getAvailableBalance()
        );

        ArgumentCaptor<Movement> movementCaptor = ArgumentCaptor.forClass(Movement.class);
        verify(movementRepository).saveMovement(movementCaptor.capture());

        Movement savedMovement = movementCaptor.getValue();
        org.junit.jupiter.api.Assertions.assertEquals(MovementType.DEBIT, savedMovement.getType());
        org.junit.jupiter.api.Assertions.assertEquals(new BigDecimal("5.00"), savedMovement.getValue());
        org.junit.jupiter.api.Assertions.assertEquals("123", savedMovement.getAccountNumber());
        org.junit.jupiter.api.Assertions.assertEquals(new BigDecimal("5.00"), savedMovement.getBalance());
    }

    @Test
    void shouldCreateCreditMovementAndUpdateAvailableBalance() {
        Account account = new Account(
                "123",
                AccountType.SAVINGS,
                new BigDecimal("10.00"),
                new BigDecimal("10.00"),
                true,
                "1723456781"
        );

        CreateMovementDTO dto = new CreateMovementDTO(
                "123",
                MovementType.CREDIT,
                new BigDecimal("7.00")
        );

        when(accountRepository.findAccountById("123")).thenReturn(Mono.just(account));
        when(accountRepository.saveAccount(any(Account.class))).thenReturn(Mono.empty());
        when(movementRepository.saveMovement(any(Movement.class))).thenReturn(Mono.empty());

        StepVerifier.create(service.createMovement(dto))
                .verifyComplete();

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).saveAccount(accountCaptor.capture());

        org.junit.jupiter.api.Assertions.assertEquals(
                new BigDecimal("17.00"),
                accountCaptor.getValue().getAvailableBalance()
        );

        ArgumentCaptor<Movement> movementCaptor = ArgumentCaptor.forClass(Movement.class);
        verify(movementRepository).saveMovement(movementCaptor.capture());

        org.junit.jupiter.api.Assertions.assertEquals(
                new BigDecimal("17.00"),
                movementCaptor.getValue().getBalance()
        );
    }

    @Test
    void shouldFailWhenAccountDoesNotExist() {
        CreateMovementDTO dto = new CreateMovementDTO(
                "999",
                MovementType.DEBIT,
                new BigDecimal("5.00")
        );

        when(accountRepository.findAccountById("999")).thenReturn(Mono.empty());

        StepVerifier.create(service.createMovement(dto))
                .expectError(AccountNotFoundException.class)
                .verify();

        verify(movementRepository, never()).saveMovement(any());
        verify(accountRepository, never()).saveAccount(any());
    }

    @Test
    void shouldFailWhenValueIsZeroOrNegative() {
        CreateMovementDTO dto = new CreateMovementDTO(
                "123",
                MovementType.DEBIT,
                BigDecimal.ZERO
        );

        StepVerifier.create(service.createMovement(dto))
                .expectErrorMatches(err ->
                        err instanceof IllegalArgumentException &&
                        err.getMessage().equals("Value must be greater than zero")
                )
                .verify();

        verify(movementRepository, never()).saveMovement(any());
        verify(accountRepository, never()).saveAccount(any());
    }

    @Test
    void shouldFailWhenAccountNumberIsBlank() {
        CreateMovementDTO dto = new CreateMovementDTO(
                "   ",
                MovementType.DEBIT,
                new BigDecimal("5.00")
        );

        StepVerifier.create(service.createMovement(dto))
                .expectErrorMatches(err ->
                        err instanceof IllegalArgumentException &&
                        err.getMessage().equals("Account number cannot be null or blank")
                )
                .verify();

        verify(movementRepository, never()).saveMovement(any());
        verify(accountRepository, never()).saveAccount(any());
    }

    @Test
    void shouldFailWhenDebitHasInsufficientFunds() {
        Account account = new Account(
                "123",
                AccountType.SAVINGS,
                new BigDecimal("5.00"),
                new BigDecimal("5.00"),
                true,
                "1723456781"
        );

        CreateMovementDTO dto = new CreateMovementDTO(
                "123",
                MovementType.DEBIT,
                new BigDecimal("10.00")
        );

        when(accountRepository.findAccountById("123")).thenReturn(Mono.just(account));

        StepVerifier.create(service.createMovement(dto))
                .expectError(InsufficientBalanceException.class)
                .verify();

        verify(movementRepository, never()).saveMovement(any());
        verify(accountRepository, never()).saveAccount(any());
    }
}