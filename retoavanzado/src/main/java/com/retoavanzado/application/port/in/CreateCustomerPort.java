package com.retoavanzado.application.port.in;

import com.retoavanzado.domain.dto.CustomerDTO;
import reactor.core.publisher.Mono;

public interface CreateCustomerPort {
    Mono<Void> createCustomer(CustomerDTO customerDTO);
}