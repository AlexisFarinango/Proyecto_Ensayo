package com.microservicioam.application.port.out;

import com.microservicioam.domain.models.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepositoryPort {
    Mono<Void> saveAccount(Account account);

    Mono<Boolean> existsByNumber(String number);

    Mono<Void> deleteAccountbyId(String number);

    Flux<Account>findByCustomerIdentification(String identification); 

    Mono<Account> findAccountById(String number);


}
