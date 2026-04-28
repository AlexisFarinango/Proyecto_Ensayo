package com.microservicioam.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.microservicioam.domain.models.Movement;
import com.microservicioam.domain.models.types.MovementType;
import com.microservicioam.infrastructure.adapter.out.persistence.entity.MovementEntity;

@Component
public class MovementEntityMapper {
    public Movement toDomain(MovementEntity movementEntity) {
        return new Movement(
            movementEntity.getMovementDate(),
            MovementType.valueOf(movementEntity.getType()),
            movementEntity.getValue(),
            movementEntity.getBalance(),
            movementEntity.getAccountNumber()
        );
    }

    public MovementEntity toEntity(Movement movement) {
        MovementEntity entity = new MovementEntity();
        entity.setMovementDate(movement.getMovementDate());
        entity.setType(movement.getType().name());
        entity.setValue(movement.getValue());
        entity.setBalance(movement.getBalance());
        entity.setAccountNumber(movement.getAccountNumber());
        return entity;
    }

}
