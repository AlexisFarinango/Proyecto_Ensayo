package com.microservicioam.infrastructure.adapter.out.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "movements")
@Getter
@Setter
public class MovementEntity {
    @Id
    private Long id;
    @Column("movement_date")
    private LocalDateTime movementDate;
    private String type;
    private BigDecimal value;
    private BigDecimal balance;
    @Column("account_number")
    private String accountNumber;

}
