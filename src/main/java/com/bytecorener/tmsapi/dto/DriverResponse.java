package com.bytecorener.tmsapi.dto;

public record DriverResponse(
        Long id,
        UserResponse user,
        String licenseNumber,
        String phoneNumber,
        boolean available
) {
}
