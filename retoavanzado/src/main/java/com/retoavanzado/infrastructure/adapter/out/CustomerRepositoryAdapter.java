package com.retoavanzado.infrastructure.adapter.out;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.retoavanzado.application.port.out.CustomerRepositoryPort;
import com.retoavanzado.domain.models.Customer;
import com.retoavanzado.infrastructure.adapter.out.mapper.CustomerEntityMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {
    private final R2DBCCustomerRepository R2DBCcustomerRepository;

    private final Logger log = LoggerFactory.getLogger(CustomerRepositoryAdapter.class);

    public CustomerRepositoryAdapter(R2DBCCustomerRepository R2DBCcustomerRepository) {
        this.R2DBCcustomerRepository = R2DBCcustomerRepository;
    }

    @Override
    public Mono<Void> saveCustomer(Customer customer) {
        return R2DBCcustomerRepository.existsById(customer.getIdentification())
        .flatMap(exists -> {

            CustomerEntity entity = new CustomerEntity();
            entity.setIdentification(customer.getIdentification());
            entity.setName(customer.getName());
            entity.setGender(customer.getGender());
            entity.setAddress(customer.getAddress());
            entity.setPhone(customer.getPhone());
            entity.setPassword(customer.getPassword());
            entity.setStatus(customer.getStatus());

            if (exists) {
                entity.setIsNew(false); // UPDATE
            } else {
                entity.setIsNew(true);  // INSERT
            }

            return R2DBCcustomerRepository.save(entity).then();
        });
    }


    @Override
    public Mono<Customer> findCustomerByIdentification(String identification){
        log.info("Finding Customer with identification: {}", identification);
        return R2DBCcustomerRepository.findById(identification).map(CustomerEntityMapper::toDomain);
    }

    @Override
    public Flux<Customer> findAllCustomers(){
        log.info("Finding all Customers");
        return R2DBCcustomerRepository.findAll().map(CustomerEntityMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByIdentification(String identification){
        log.info("Checking existence of Customer with identification: {}", identification);
        return R2DBCcustomerRepository.existsById(identification);
    }

    @Override
    public Mono<Void> deleteCustomerByIdentification(String identification){
        log.info("Deleting Customer with identification: {}", identification);
        return R2DBCcustomerRepository.deleteById(identification);
    }


}
