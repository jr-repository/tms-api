package com.bytecorener.tmsapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ShipmentOrderRequest(
        @NotNull(message = "Customer ID is required")
        Long customerId,

        @NotBlank(message = "Pickup address is required")
        String pickupAddress,

        @NotBlank(message = "Delivery address is required")
        String deliveryAddress,

        @NotBlank(message = "Cargo description is required")
        String cargoDescription,

        @Positive(message = "Weight must be greater than zero")
        Double weightKg
) {
}
