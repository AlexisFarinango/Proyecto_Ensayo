package com.retoavanzado.application.port.in;

import reactor.core.publisher.Mono;

public interface DeleteCustomerPort {
    Mono<Void> deleteCustomer(String identification);
}
