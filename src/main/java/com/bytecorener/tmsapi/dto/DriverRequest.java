package com.bytecorener.tmsapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DriverRequest(
        @NotNull(message = "User ID is required")
        Long userId,

        @NotBlank(message = "License number is required")
        String licenseNumber,

        String phoneNumber,
        Boolean available
) {
}
