package com.retoavanzado.infrastructure.adapter.in.error;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import com.retoavanzado.application.exception.BadRequestException;
import com.retoavanzado.application.exception.CustomerAlreadyExistsException;
import com.retoavanzado.application.exception.CustomerNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleCustomerAlreadyExistsException(
                    CustomerAlreadyExistsException ex,
                    ServerWebExchange exchange) {

            ApiErrorResponse responsebody = new ApiErrorResponse(
                            LocalDateTime.now(),
                            HttpStatus.CONFLICT.value(), "CONFLICT",
                            ex.getMessage(),
                            exchange.getRequest().getPath().value());
            log.warn("Customer already exists - message: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responsebody);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleCustomerNotFoundException(
        CustomerNotFoundException ex,
        ServerWebExchange exchange
    ){

        ApiErrorResponse responsebody = new ApiErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),"NOT_FOUND",
            ex.getMessage(),
            exchange.getRequest().getPath().value()
        ); 
        log.warn("Customer not found: {}", ex.getMessage()); 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsebody);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(
        BadRequestException ex,
        ServerWebExchange exchange
    ){     
        ApiErrorResponse responsebody = new ApiErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "BAD_REQUEST",
            ex.getMessage(),
            exchange.getRequest().getPath().value()
        ); 
        log.warn("Illegal argument - message={}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsebody);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleDataAccess(
        DataAccessException ex,
        ServerWebExchange exchange
    ){
        ApiErrorResponse responsebody = new ApiErrorResponse(
            LocalDateTime.now(),
            HttpStatus.SERVICE_UNAVAILABLE.value(),
            "SERVICE_UNAVAILABLE",
            "Database temporarily unavailable. Please try again later.",
            exchange.getRequest().getPath().value()
        );
        log.error("Database access error - path={}", exchange.getRequest().getPath().value(), ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(responsebody);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(
        Exception ex,
        ServerWebExchange exchange
    ){

        
        ApiErrorResponse responsebody = new ApiErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "INTERNAL_SERVER_ERROR",
            "An unexpected error occurred.",
            exchange.getRequest().getPath().value()
        ); 
        log.error("Unexpected error - path={}", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsebody);

    }
}
