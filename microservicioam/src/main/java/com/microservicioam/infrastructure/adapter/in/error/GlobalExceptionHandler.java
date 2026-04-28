package com.microservicioam.infrastructure.adapter.in.error;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;

import com.microservicioam.application.exception.AccountAlreadyExistsException;
import com.microservicioam.application.exception.AccountNotFoundException;
import com.microservicioam.application.exception.BadRequestException;
import com.microservicioam.application.exception.CustomerNotFoundException;
import com.microservicioam.application.exception.InsufficientBalanceException;

@RestControllerAdvice
public class GlobalExceptionHandler {
        private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(AccountNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleAccountNotFoundException(
                        AccountNotFoundException ex,
                        ServerWebExchange exchange) {

                ApiErrorResponse responsebody = new ApiErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.NOT_FOUND.value(), "NOT_FOUND",
                                ex.getMessage(),
                                exchange.getRequest().getPath().value());
                log.warn("Account not found - message: {}", ex.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsebody);
        }

        @ExceptionHandler(AccountAlreadyExistsException.class)
        public ResponseEntity<ApiErrorResponse> handleAccountAlreadyExistsException(
                        AccountAlreadyExistsException ex,
                        ServerWebExchange exchange) {

                ApiErrorResponse responsebody = new ApiErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.CONFLICT.value(), "CONFLICT",
                                ex.getMessage(),
                                exchange.getRequest().getPath().value());
                log.warn("Account already exists - message: {}", ex.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(responsebody);
        }

        @ExceptionHandler(InsufficientBalanceException.class)
        public ResponseEntity<ApiErrorResponse> handleInsufficientBalanceException(
                        InsufficientBalanceException ex,
                        ServerWebExchange exchange) {

                ApiErrorResponse responsebody = new ApiErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.CONFLICT.value(), "CONFLICT",
                                ex.getMessage(),
                                exchange.getRequest().getPath().value());
                log.warn("Insufficient balance - message: {}", ex.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(responsebody);
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ApiErrorResponse> handleBadRequestException(
                        BadRequestException ex,
                        ServerWebExchange exchange) {
                ApiErrorResponse responsebody = new ApiErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                "BAD_REQUEST",
                                ex.getMessage(),
                                exchange.getRequest().getPath().value());
                log.warn("Illegal argument - message: {}", ex.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsebody);
        }

        @ExceptionHandler(CustomerNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleCustomerNotFound(
                        CustomerNotFoundException ex,
                        ServerWebExchange exchange) {
                ApiErrorResponse body = new ApiErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.NOT_FOUND.value(),
                                "CUSTOMER_NOT_FOUND",
                                ex.getMessage(),
                                exchange.getRequest().getPath().value());
                log.warn("Error in the Customers microservices - path={} message={}",
                                exchange.getRequest().getPath().value(), ex.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }

        @ExceptionHandler(ServerWebInputException.class)
        public ResponseEntity<ApiErrorResponse> handleServerWebInput(
                        ServerWebInputException ex,
                        ServerWebExchange exchange) {
                ApiErrorResponse body = new ApiErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                "BAD_REQUEST",
                                "Invalid request body. Check field types/values (e.g. MovementType: CREDIT or DEBIT).",
                                exchange.getRequest().getPath().value());

                // log corto SIN stacktrace
                log.warn("Bad request - path={} message={}",
                                exchange.getRequest().getPath().value(),
                                ex.getReason() != null ? ex.getReason() : ex.getMessage());

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }

        @ExceptionHandler(DataAccessException.class)
        public ResponseEntity<ApiErrorResponse> handleDataAccess(
                        DataAccessException ex,
                        ServerWebExchange exchange) {
                ApiErrorResponse responsebody = new ApiErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.SERVICE_UNAVAILABLE.value(),
                                "SERVICE_UNAVAILABLE",
                                "Database temporarily unavailable. Please try again later.",
                                exchange.getRequest().getPath().value());
                log.error("Database access error - path={}", exchange.getRequest().getPath().value(), ex);
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(responsebody);

        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleGeneralException(
                        Exception ex,
                        ServerWebExchange exchange) {

                ApiErrorResponse responsebody = new ApiErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "INTERNAL_SERVER_ERROR",
                                "An unexpected error occurred.",
                                exchange.getRequest().getPath().value());
                log.error("Unexpected error - path={}", ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsebody);

        }

}
