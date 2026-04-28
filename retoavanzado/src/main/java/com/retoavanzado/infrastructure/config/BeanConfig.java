package com.retoavanzado.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.retoavanzado.application.port.in.CreateCustomerPort;
import com.retoavanzado.application.port.in.DeleteCustomerPort;
import com.retoavanzado.application.port.in.GetCustomerPort;
import com.retoavanzado.application.port.in.UpdateCustomerPort;
import com.retoavanzado.application.port.out.CustomerRepositoryPort;
import com.retoavanzado.application.service.customer.CreateCustomerService;
import com.retoavanzado.application.service.customer.DeleteCustomerService;
import com.retoavanzado.application.service.customer.GetCustomerService;
import com.retoavanzado.application.service.customer.UpdateCustomerService;

@Configuration
public class BeanConfig {
    @Bean
    public CreateCustomerPort createCustomerPort(CustomerRepositoryPort customerRepositoryPort) {
        return new CreateCustomerService(customerRepositoryPort);

    }

    @Bean
    public GetCustomerPort getCustomerPort(CustomerRepositoryPort customerRepositoryPort) {
        return new GetCustomerService(customerRepositoryPort);
    }

    @Bean
    public UpdateCustomerPort updateCustomerPort(CustomerRepositoryPort customerRepositoryPort){
        return new UpdateCustomerService(customerRepositoryPort);
    }

    @Bean
    public DeleteCustomerPort deleteCustomerPort(CustomerRepositoryPort customerRepositoryPort){
        return new DeleteCustomerService(customerRepositoryPort);
    }

}
