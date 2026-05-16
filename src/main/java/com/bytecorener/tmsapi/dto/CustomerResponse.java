package com.bytecorener.tmsapi.dto;

public record CustomerResponse(
        Long id,
        UserResponse user,
        String companyName,
        String phoneNumber,
        String address
) {
}
