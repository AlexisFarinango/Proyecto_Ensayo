package com.retoavanzado.application.service.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.retoavanzado.application.exception.CustomerNotFoundException;
import com.retoavanzado.application.port.in.UpdateCustomerPort;
import com.retoavanzado.application.port.out.CustomerRepositoryPort;
import com.retoavanzado.domain.dto.UpdateCustomerDTO;

import reactor.core.publisher.Mono;

public class UpdateCustomerService implements UpdateCustomerPort{

    private final CustomerRepositoryPort customerRepositoryPort;
    private final Logger log = LoggerFactory.getLogger(UpdateCustomerService.class);

    public UpdateCustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public Mono<Void> updateCustomer(String identification, UpdateCustomerDTO customerDTO ){
        
        return customerRepositoryPort.findCustomerByIdentification(identification)
            .switchIfEmpty(Mono.error(new CustomerNotFoundException(identification)))
            .flatMap(customerupdate -> {     

                if(customerDTO.name() !=null) customerupdate.changeName(customerDTO.name());
                if(customerDTO.gender() !=null) customerupdate.changeGender(customerDTO.gender());
                if(customerDTO.address() !=null) customerupdate.changeAddress(customerDTO.address());
                if(customerDTO.phone() !=null) customerupdate.changePhone(customerDTO.phone());
                if(customerDTO.password() !=null) customerupdate.changePassword(customerDTO.password());
                if(customerDTO.status() !=null) customerupdate.changeStatus(customerDTO.status());
                
                
                log.info("Update Customer with identification {}", identification);
                return customerRepositoryPort.saveCustomer(customerupdate);
            });

    }
}
