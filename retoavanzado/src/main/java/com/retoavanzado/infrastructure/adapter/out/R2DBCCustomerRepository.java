package com.retoavanzado.infrastructure.adapter.out;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface R2DBCCustomerRepository extends ReactiveCrudRepository<CustomerEntity, String> {
    Mono<Boolean> existsByIdentification(String identification); 
}
