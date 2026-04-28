package com.microservicioam.infrastructure.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.microservicioam.application.port.out.AccountRepositoryPort;
import com.microservicioam.domain.models.Account;
import com.microservicioam.infrastructure.adapter.out.persistence.entity.AccountEntity;
import com.microservicioam.infrastructure.adapter.out.persistence.mapper.AccountEntityMapper;
import com.microservicioam.infrastructure.adapter.out.persistence.repository.R2DBCAccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountAdapter implements AccountRepositoryPort{

    private final R2DBCAccountRepository R2DBCAccountRepository;
    private final AccountEntityMapper accountEntityMapper;

    public AccountAdapter(R2DBCAccountRepository R2DBCAccountRepository, AccountEntityMapper accountEntityMapper) {
        this.R2DBCAccountRepository = R2DBCAccountRepository;
        this.accountEntityMapper = accountEntityMapper;
    }

    @Override
    public Mono<Void> saveAccount(Account account) {
        return R2DBCAccountRepository.existsById(account.getNumber())
            .flatMap(exists ->{
                AccountEntity accountEntity = new AccountEntity();
                accountEntity.setNumber(account.getNumber());
                accountEntity.setType(account.getType().name());
                accountEntity.setInitialBalance(account.getInitialBalance());
                accountEntity.setAvailableBalance(account.getAvailableBalance());
                accountEntity.setStatus(account.getStatus());
                accountEntity.setCustomerIdentification(account.getCustomerIdentification());

                if(exists){
                    accountEntity.setIsNew(false);
                }else{
                    accountEntity.setIsNew(true);
                }
                
                return R2DBCAccountRepository.save(accountEntity).then();
            });
    };

    @Override
    public Mono<Boolean> existsByNumber(String number) {
        return R2DBCAccountRepository.existsById(number);
    };

    @Override
    public Mono<Void> deleteAccountbyId(String number){
        return R2DBCAccountRepository.deleteById(number);
    }

    @Override
    public Mono<Account> findAccountById(String number){
        return R2DBCAccountRepository.findById(number)
            .map(accountEntityMapper::toDomain);
    }

    @Override
    public Flux<Account> findByCustomerIdentification(String Identification){
        return R2DBCAccountRepository.findByCustomerIdentification(Identification)
            .map(accountEntityMapper::toDomain);
    }

}
