package com.microservicioam.application.port.in;

import com.microservicioam.domain.models.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetAccountPort {
    Mono<Account> getAccountByid(String number);
    Flux<Account> getAllAccountByCustomer(String identificarion);

}
