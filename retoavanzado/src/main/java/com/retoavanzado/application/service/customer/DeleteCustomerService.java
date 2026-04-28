package com.retoavanzado.application.service.customer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.retoavanzado.application.exception.CustomerNotFoundException;
import com.retoavanzado.application.port.in.DeleteCustomerPort;
import com.retoavanzado.application.port.out.CustomerRepositoryPort;

import reactor.core.publisher.Mono;

public class DeleteCustomerService implements DeleteCustomerPort{

    private final CustomerRepositoryPort customerRepositoryPort;

    private final Logger log = LoggerFactory.getLogger(DeleteCustomerService.class);
    public DeleteCustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    };

    @Override
    public Mono<Void> deleteCustomer(String identification) {
        return customerRepositoryPort.existsByIdentification(identification)
        .flatMap(exists -> exists
            ? customerRepositoryPort.deleteCustomerByIdentification(identification)
                .doOnSuccess(v -> log.info("Customer deleted: {}", identification))
            : Mono.error(new CustomerNotFoundException(identification))
        );
    }


}
