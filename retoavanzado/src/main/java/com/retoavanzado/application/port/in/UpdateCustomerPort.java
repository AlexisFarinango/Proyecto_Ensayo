package com.retoavanzado.application.port.in;
import com.retoavanzado.domain.dto.UpdateCustomerDTO;

import reactor.core.publisher.Mono;

public interface UpdateCustomerPort {
    Mono<Void> updateCustomer(String identification, UpdateCustomerDTO customerDTO);
}
