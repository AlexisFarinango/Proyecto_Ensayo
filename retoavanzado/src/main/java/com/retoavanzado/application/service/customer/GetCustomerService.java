package com.retoavanzado.application.service.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.retoavanzado.application.exception.CustomerNotFoundException;
import com.retoavanzado.application.port.in.GetCustomerPort;
import com.retoavanzado.application.port.out.CustomerRepositoryPort;
import com.retoavanzado.domain.models.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetCustomerService implements GetCustomerPort {
    
    private final CustomerRepositoryPort repositoryCustomer;

    private final Logger log = LoggerFactory.getLogger(GetCustomerPort.class);

    public GetCustomerService (CustomerRepositoryPort repositoryCustomer) {
        this.repositoryCustomer = repositoryCustomer;
    }

    @Override
    public Mono<Customer> getCustomerByIdentification(String Identification) {
        return repositoryCustomer.findCustomerByIdentification(Identification)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(Identification)))
                .doOnSuccess(customer -> log.info("Customer found by Identification: {}",Identification));
    }

    @Override
    public Flux<Customer> getAllCustomers(){
        log.info("Get All Customers Successfully");
        return repositoryCustomer.findAllCustomers();
    }

    
}
