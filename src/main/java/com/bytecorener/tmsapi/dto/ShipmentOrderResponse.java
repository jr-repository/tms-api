package com.bytecorener.tmsapi.dto;

import com.bytecorener.tmsapi.enumtype.ShipmentStatus;

import java.time.LocalDateTime;

public record ShipmentOrderResponse(
        Long id,
        String orderNumber,
        CustomerResponse customer,
        String pickupAddress,
        String deliveryAddress,
        String cargoDescription,
        Double weightKg,
        ShipmentStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
