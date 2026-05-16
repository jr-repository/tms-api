package com.bytecorener.tmsapi.dto;

import jakarta.validation.constraints.NotNull;

public record AssignmentRequest(
        @NotNull(message = "Driver ID is required")
        Long driverId,

        @NotNull(message = "Vehicle ID is required")
        Long vehicleId
) {
}
