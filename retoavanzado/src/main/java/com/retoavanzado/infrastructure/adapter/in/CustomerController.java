package com.retoavanzado.infrastructure.adapter.in;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retoavanzado.application.port.in.CreateCustomerPort;
import com.retoavanzado.application.port.in.DeleteCustomerPort;
import com.retoavanzado.application.port.in.GetCustomerPort;
import com.retoavanzado.application.port.in.UpdateCustomerPort;
import com.retoavanzado.domain.dto.CustomerDTO;
import com.retoavanzado.domain.dto.UpdateCustomerDTO;
import com.retoavanzado.domain.models.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/customers")

public class CustomerController {
    private final CreateCustomerPort createCustomerPort;
    private final GetCustomerPort getCustomerPort;
    private final UpdateCustomerPort updateCustomerPort;
    private final DeleteCustomerPort deleteCustomerPort;

    public CustomerController(CreateCustomerPort createCustomerPort, GetCustomerPort getCustomerPort, UpdateCustomerPort updateCustomerPort, DeleteCustomerPort deleteCustomerPort) {
        this.createCustomerPort = createCustomerPort;
        this.getCustomerPort = getCustomerPort;
        this.updateCustomerPort = updateCustomerPort;
        this.deleteCustomerPort = deleteCustomerPort;
    };

    @PostMapping
    public Mono<Void> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return createCustomerPort.createCustomer(customerDTO);
    };

    @GetMapping("/{identification}")
    public Mono<Customer> getCustomerByIdentification(@PathVariable String identification){
        return getCustomerPort.getCustomerByIdentification(identification);
    };

    @GetMapping
    public Flux<Customer> getAllCustomers(){
        return getCustomerPort.getAllCustomers();
    };

    @PutMapping("/{identification}")
    public Mono<Void> updateCustomer(@PathVariable String identification, @RequestBody UpdateCustomerDTO customerDTO){
        return updateCustomerPort.updateCustomer(identification, customerDTO);
    };

    @DeleteMapping("/{identification}")
    public Mono<Void> deleteCustomer(@PathVariable String identification){
        return deleteCustomerPort.deleteCustomer(identification);
    };

}
