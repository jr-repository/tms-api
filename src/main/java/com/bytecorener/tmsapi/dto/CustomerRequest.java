package com.bytecorener.tmsapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        @NotNull(message = "User ID is required")
        Long userId,

        @NotBlank(message = "Company name is required")
        String companyName,

        String phoneNumber,
        String address
) {
}
