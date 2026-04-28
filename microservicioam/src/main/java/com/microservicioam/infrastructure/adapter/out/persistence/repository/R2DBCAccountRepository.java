package com.microservicioam.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.microservicioam.infrastructure.adapter.out.persistence.entity.AccountEntity;

import reactor.core.publisher.Flux;

public interface R2DBCAccountRepository extends ReactiveCrudRepository<AccountEntity, String> {
    Flux<AccountEntity> findByCustomerIdentification(String customerIdentification);

}
