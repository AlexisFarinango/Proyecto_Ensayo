package com.microservicioam.domain.services;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.microservicioam.domain.models.types.MovementType;

@Component
public class MovementCalculatorFactory {

    private final Map<MovementType, MovementCalculator> strategies;

    public MovementCalculatorFactory(){
        this.strategies = new EnumMap<>(MovementType.class);
        this.strategies.put(MovementType.DEBIT, new DebitMovementCalculator());
        this.strategies.put(MovementType.CREDIT, new CreditMovementCalculator());
    }

    public MovementCalculator get(MovementType movementType){
        MovementCalculator calculator = strategies.get(movementType);
        if(calculator==null){
            throw new IllegalArgumentException("Unsupported movement type: " + movementType);
        }
        return calculator;
    }

}
