package com.bytecorener.tmsapi.dto;

import com.bytecorener.tmsapi.enumtype.VehicleStatus;

public record VehicleResponse(
        Long id,
        String plateNumber,
        String vehicleType,
        Double capacityKg,
        VehicleStatus status
) {
}
