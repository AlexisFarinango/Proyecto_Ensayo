package com.retoavanzado.application.service.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.retoavanzado.application.exception.CustomerAlreadyExistsException;
import com.retoavanzado.application.port.in.CreateCustomerPort;
import com.retoavanzado.application.port.out.CustomerRepositoryPort;
import com.retoavanzado.domain.dto.CustomerDTO;
import com.retoavanzado.domain.models.Customer;

import reactor.core.publisher.Mono;

@Service
public class CreateCustomerService implements CreateCustomerPort{
    private final CustomerRepositoryPort repositoryCustomer;

    private final Logger log = LoggerFactory.getLogger(CreateCustomerService.class);

    
    public CreateCustomerService(CustomerRepositoryPort repositoryCustomer) {
        this.repositoryCustomer = repositoryCustomer;
    }
    
    @Override
    public Mono<Void> createCustomer(CustomerDTO customerDTO){
        return repositoryCustomer.existsByIdentification(customerDTO.identification())
        .flatMap(exists -> {
            if(exists){
                return Mono.error(new CustomerAlreadyExistsException(customerDTO.identification()));
            }
            Customer customer = new Customer(
                customerDTO.name(),
                customerDTO.gender(),
                customerDTO.identification(),
                customerDTO.address(),
                customerDTO.phone(),
                customerDTO.password(),
                customerDTO.status()
            );
            log.info("Customer created: {}", customer.getIdentification());
            return repositoryCustomer.saveCustomer(customer);
        });

    }

}
