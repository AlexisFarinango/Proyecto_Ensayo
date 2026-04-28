package com.microservicioam.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicioam.application.mapper.report.ReportMapper;
import com.microservicioam.application.port.in.CreateAccountPort;
import com.microservicioam.application.port.in.CreateMovementPort;
import com.microservicioam.application.port.in.DeleteAccountPort;
import com.microservicioam.application.port.in.GetAccountPort;
import com.microservicioam.application.port.in.GetAccountStatementReportPort;
import com.microservicioam.application.port.in.UpdateAccountPort;
import com.microservicioam.application.port.out.AccountRepositoryPort;
import com.microservicioam.application.port.out.CustomerValidationPort;
import com.microservicioam.application.port.out.MovementRepositoryPort;
import com.microservicioam.application.port.out.ReportRepositoryPort;
import com.microservicioam.application.service.CreateAccountService;
import com.microservicioam.application.service.CreateMovementService;
import com.microservicioam.application.service.DeleteAccountService;
import com.microservicioam.application.service.GetAccountService;
import com.microservicioam.application.service.GetAccountStatementReportService;
import com.microservicioam.application.service.UpdateAccountService;
import com.microservicioam.domain.services.MovementCalculatorFactory;
import com.microservicioam.infrastructure.adapter.out.rest.CustomerRestClientAdapter;

@Configuration
public class BeanConfig {

    @Bean
    public CustomerValidationPort customerValidationPort(WebClient customerWebClient) {
        return new CustomerRestClientAdapter(customerWebClient);
    }

    @Bean
    public CreateAccountPort createAccountPort(AccountRepositoryPort accountRepo, CustomerValidationPort customerValidationPort) {
        return new CreateAccountService(accountRepo, customerValidationPort);
    }

    @Bean
    public DeleteAccountPort deleteAccountPort(AccountRepositoryPort accountRepo){
        return new DeleteAccountService(accountRepo);
    }

    @Bean
    public GetAccountPort getAccountPort(AccountRepositoryPort accountRepo, CustomerValidationPort customerValidationPort){
        return new GetAccountService(accountRepo, customerValidationPort);
    }

    @Bean
    public UpdateAccountPort updateAccountPort(AccountRepositoryPort accountRepo){
        return new UpdateAccountService(accountRepo);
    }

    @Bean
    public CreateMovementPort createMovementPort(MovementRepositoryPort movementRepo, AccountRepositoryPort accountRepo,MovementCalculatorFactory calculatorFactory){
        return new CreateMovementService(movementRepo, accountRepo, calculatorFactory);
    }

    @Bean
    public MovementCalculatorFactory movementCalculatorFactory() {
        return new MovementCalculatorFactory();
    }

    @Bean
    public GetAccountStatementReportPort getAccountStatementReportPort(ReportRepositoryPort reportRepo, AccountRepositoryPort accountRepo, CustomerValidationPort customerValidationPort, ReportMapper reportMapper){
        return new GetAccountStatementReportService(reportRepo, accountRepo, customerValidationPort, reportMapper);
    }

}
