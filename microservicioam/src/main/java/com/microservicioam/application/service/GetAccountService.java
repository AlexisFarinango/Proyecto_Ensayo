package com.microservicioam.application.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.microservicioam.application.exception.AccountNotFoundException;
import com.microservicioam.application.exception.CustomerNotFoundException;
import com.microservicioam.application.port.in.GetAccountPort;
import com.microservicioam.application.port.out.AccountRepositoryPort;
import com.microservicioam.application.port.out.CustomerValidationPort;
import com.microservicioam.domain.models.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GetAccountService implements GetAccountPort {
    public final AccountRepositoryPort accountRepositoryPort;
    public final CustomerValidationPort customerValidationPort;

    public final Logger log = LoggerFactory.getLogger(GetAccountService.class);

    public GetAccountService(AccountRepositoryPort accountRepositoryPort,
            CustomerValidationPort customerValidationPort) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.customerValidationPort = customerValidationPort;
    }

    @Override
    public Mono<Account> getAccountByid(String number) {
        return accountRepositoryPort.findAccountById(number)
                .switchIfEmpty(Mono.error(new AccountNotFoundException(number)))
                .doOnSuccess(account -> log.info("Account found by number: {}", number));
    }

    @Override
    public Flux<Account> getAllAccountByCustomer(String identification) {

        return customerValidationPort.existsCustomerByIdentification(identification)
                .flatMapMany(exists -> {
                    if (!exists) {
                        return Flux.error(new CustomerNotFoundException(identification));
                    }
                    return accountRepositoryPort
                            .findByCustomerIdentification(identification);
                })
                .doOnComplete(() -> log.info("Accounts found by customer identification: {}", identification));
    }

}
