package com.bytecorener.tmsapi.dto;

public record LiveShipmentStatusMessage(
        Long shipmentOrderId,
        String orderNumber,
        String status,
        String note
) {
}
