package com.retoavanzado.infrastructure.adapter.out.mapper;

import org.springframework.stereotype.Component;

import com.retoavanzado.domain.models.Customer;
import com.retoavanzado.infrastructure.adapter.out.CustomerEntity;

@Component
public class CustomerEntityMapper {

    public static Customer toDomain(CustomerEntity customerEntity){
        return new Customer(
            customerEntity.getName(),
            customerEntity.getGender(),
            customerEntity.getIdentification(),
            customerEntity.getAddress(),
            customerEntity.getPhone(),
            customerEntity.getPassword(),
            customerEntity.isStatus()
        );
    }

}
