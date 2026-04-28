package com.microservicioam.infrastructure.adapter.out.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicioam.application.port.out.CustomerValidationPort;

import reactor.core.publisher.Mono;

public class CustomerRestClientAdapter implements CustomerValidationPort{

    private final WebClient webClient;

    private final Logger log = LoggerFactory.getLogger(CustomerRestClientAdapter.class);

    public CustomerRestClientAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Boolean> existsCustomerByIdentification(String identification) {
        return webClient.get()
                .uri("/api/v1/customers/{id}", identification)
                .exchangeToMono(resp -> {
                    if (resp.statusCode().equals(HttpStatus.OK)) return Mono.just(true);
                    if (resp.statusCode().equals(HttpStatus.NOT_FOUND)) return Mono.just(false);
                    return Mono.error(new RuntimeException("Customer service error"));
                })
                .doOnNext(exists -> log.info("Customer existence check id={} exists={}", identification, exists))
                ;
            
    }

}
