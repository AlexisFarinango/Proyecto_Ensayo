package com.retoavanzado.application.port.out;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.retoavanzado.domain.models.Customer;


public interface CustomerRepositoryPort {

    //create and update
    Mono<Void> saveCustomer(Customer customer);
    //Get
    Mono<Customer>findCustomerByIdentification(String identification);
    Flux<Customer> findAllCustomers();

    Mono<Void> deleteCustomerByIdentification(String identification);

    Mono<Boolean> existsByIdentification(String identification);

}
