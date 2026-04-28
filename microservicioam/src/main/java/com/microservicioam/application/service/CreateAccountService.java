package com.microservicioam.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.microservicioam.application.exception.AccountAlreadyExistsException;
import com.microservicioam.application.exception.CustomerNotFoundException;
import com.microservicioam.application.port.in.CreateAccountPort;
import com.microservicioam.application.port.out.AccountRepositoryPort;
import com.microservicioam.application.port.out.CustomerValidationPort;
import com.microservicioam.domain.dto.CreateAccountDTO;
import com.microservicioam.domain.models.Account;

import reactor.core.publisher.Mono;

@Service
public class CreateAccountService implements CreateAccountPort {

    private final AccountRepositoryPort accountRepositoryPort;
    private final CustomerValidationPort customerValidationPort;
    private final Logger log = LoggerFactory.getLogger(CreateAccountService.class);

    public CreateAccountService(AccountRepositoryPort accountRepositoryPort,
            CustomerValidationPort customerValidationPort) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.customerValidationPort = customerValidationPort;
    }

    @Override
    public Mono<Void> createAccount(CreateAccountDTO createAccountDTO) {
        
        return customerValidationPort
                .existsCustomerByIdentification(createAccountDTO.customerIdentification())
                .flatMap(customerExists -> {

                    if (!customerExists) {
                        return Mono.error(new CustomerNotFoundException(createAccountDTO.customerIdentification()));
                    }

                    return accountRepositoryPort
                            .existsByNumber(createAccountDTO.number());
                })
                .flatMap(accountExists -> {

                    if (accountExists) {
                        return Mono.error(new AccountAlreadyExistsException(createAccountDTO.number()));
                    }

                    Account account = new Account(
                            createAccountDTO.number(),
                            createAccountDTO.type(),
                            createAccountDTO.initialBalance(),
                            createAccountDTO.initialBalance(),
                            createAccountDTO.status(),
                            createAccountDTO.customerIdentification());

                    log.info("Account created successfully. number={} customer={}",
                            createAccountDTO.number(),
                            createAccountDTO.customerIdentification());

                    return accountRepositoryPort.saveAccount(account);
                });
    }

}
