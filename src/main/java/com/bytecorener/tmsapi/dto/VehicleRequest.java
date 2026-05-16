package com.bytecorener.tmsapi.dto;

import com.bytecorener.tmsapi.enumtype.VehicleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VehicleRequest(
        @NotBlank(message = "Plate number is required")
        String plateNumber,

        @NotBlank(message = "Vehicle type is required")
        String vehicleType,

        @Positive(message = "Capacity must be greater than zero")
        Double capacityKg,

        @NotNull(message = "Vehicle status is required")
        VehicleStatus status
) {
}
