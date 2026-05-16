package com.bytecorener.tmsapi.dto;

public record NotificationMessage(
        Long shipmentOrderId,
        String orderNumber,
        String status,
        String message
) {
}
