package com.microservicioam.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.microservicioam.domain.models.Account;
import com.microservicioam.domain.models.types.AccountType;
import com.microservicioam.infrastructure.adapter.out.persistence.entity.AccountEntity;


@Component
public class AccountEntityMapper {
    public Account toDomain(AccountEntity accountEntity) {

        return new Account(
            accountEntity.getNumber(),
            AccountType.valueOf(accountEntity.getType()),
            accountEntity.getInitialBalance(),
            accountEntity.getAvailableBalance(),
            accountEntity.getStatus(),
            accountEntity.getCustomerIdentification()
        );
    }

}
