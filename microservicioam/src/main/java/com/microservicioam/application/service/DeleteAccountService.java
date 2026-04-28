package com.microservicioam.application.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microservicioam.application.exception.AccountNotFoundException;
import com.microservicioam.application.port.in.DeleteAccountPort;
import com.microservicioam.application.port.out.AccountRepositoryPort;

import reactor.core.publisher.Mono;

public class DeleteAccountService implements DeleteAccountPort{

    private final AccountRepositoryPort accountRepositoryPort;
    private final Logger log = LoggerFactory.getLogger(DeleteAccountService.class);

    public DeleteAccountService(AccountRepositoryPort accountRepositoryPort){
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public Mono<Void> deleteAccount(String number){
        return accountRepositoryPort.existsByNumber(number)
        .flatMap(existsAccount -> existsAccount
            ? accountRepositoryPort.deleteAccountbyId(number)
                .doOnSuccess(v -> log.info("Account delete: {}",number))
            : Mono.error(new AccountNotFoundException(number))
        
        );
    }
    
}
