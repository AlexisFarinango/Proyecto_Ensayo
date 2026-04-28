package com.microservicioam.infrastructure.adapter.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicioam.application.port.in.CreateAccountPort;
import com.microservicioam.application.port.in.DeleteAccountPort;
import com.microservicioam.application.port.in.GetAccountPort;
import com.microservicioam.application.port.in.UpdateAccountPort;
import com.microservicioam.domain.dto.CreateAccountDTO;
import com.microservicioam.domain.dto.UpdateAccountDTO;
import com.microservicioam.domain.models.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController{
    private final CreateAccountPort createAccountPort;
    private final DeleteAccountPort deleteAccountPort;
    private final GetAccountPort getAccountPort;
    private final UpdateAccountPort updateAccountPort;

    public AccountController(CreateAccountPort createAccountPort, DeleteAccountPort deleteAccountPort, GetAccountPort getAccountPort, UpdateAccountPort updateAccountPort) {
        this.createAccountPort = createAccountPort;
        this.deleteAccountPort = deleteAccountPort;
        this.getAccountPort = getAccountPort;
        this.updateAccountPort = updateAccountPort;
    }

    @PostMapping
    Mono<Void> createAccount(@RequestBody CreateAccountDTO createAccountDTO ) {
        return createAccountPort.createAccount(createAccountDTO);
    }

    
    @GetMapping("/customer/{identification}")
    public Flux<Account> getAllAccountByCustomer(@PathVariable String identification) {
        return getAccountPort.getAllAccountByCustomer(identification);
    }
    
    @GetMapping("/{number}")
    public Mono<Account> getAccountByid(@PathVariable String number) {
        return getAccountPort.getAccountByid(number);
    }

    @PutMapping("/{number}")
    public Mono<Void> updateAccount(@PathVariable String number, @RequestBody UpdateAccountDTO updateAccountDTO) {
        return updateAccountPort.updateAccount(number, updateAccountDTO);
    }
    
    @DeleteMapping("/{number}")
    Mono<Void> deleteAccount(@PathVariable String number){
        return deleteAccountPort.deleteAccount(number);
    }
    

}
