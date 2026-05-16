package com.bytecorener.tmsapi.dto;

import com.bytecorener.tmsapi.enumtype.TripStatus;

import java.time.LocalDateTime;

public record TripResponse(
        Long id,
        ShipmentOrderResponse shipmentOrder,
        DriverResponse driver,
        VehicleResponse vehicle,
        LocalDateTime plannedPickupAt,
        LocalDateTime plannedDeliveryAt,
        TripStatus status
) {
}
