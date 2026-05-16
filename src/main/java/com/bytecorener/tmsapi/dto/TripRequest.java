package com.bytecorener.tmsapi.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TripRequest(
        @NotNull(message = "Shipment order ID is required")
        Long shipmentOrderId,
        LocalDateTime plannedPickupAt,
        LocalDateTime plannedDeliveryAt
) {
}
