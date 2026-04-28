package com.microservicioam.infrastructure.adapter.out.persistence.entity;


import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;


@Table(name = "accounts")
@Getter
@Setter
public class AccountEntity implements Persistable<String>{
     @Id
    private String number;
    private String type;
    private BigDecimal initialBalance;
    private BigDecimal availableBalance;
    private Boolean status;
    private String customerIdentification;

    @Transient
    private Boolean isNew = true;

    @Override
    public String getId() {
        return this.number;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

}
