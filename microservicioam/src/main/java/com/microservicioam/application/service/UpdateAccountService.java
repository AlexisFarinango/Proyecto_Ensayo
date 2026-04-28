package com.microservicioam.application.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microservicioam.application.exception.AccountNotFoundException;
import com.microservicioam.application.port.in.UpdateAccountPort;
import com.microservicioam.application.port.out.AccountRepositoryPort;
import com.microservicioam.domain.dto.UpdateAccountDTO;

import reactor.core.publisher.Mono;

public class UpdateAccountService implements UpdateAccountPort {
    public final AccountRepositoryPort accountRepositoryPort;
    private final Logger log = LoggerFactory.getLogger(UpdateAccountService.class); 

    public UpdateAccountService(AccountRepositoryPort accountRepositoryPort){
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public Mono<Void> updateAccount(String number, UpdateAccountDTO updateAccountDTO) {
        return accountRepositoryPort.findAccountById(number)
            .switchIfEmpty(Mono.error(new AccountNotFoundException(number)))
            .flatMap(existingAccount -> {
                if(updateAccountDTO.status()!=null) existingAccount.changeStatus(updateAccountDTO.status()); 
                log.info("Account status updated for account number: {}", number);
                return accountRepositoryPort.saveAccount(existingAccount);
            });
    }
}
