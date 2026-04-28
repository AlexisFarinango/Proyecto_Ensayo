package com.retoavanzado.domain.dto;


public record CustomerDTO(
    String name,
    String gender,
    String identification,
    String address,
    String phone,
    String password,
    Boolean status) {
}
