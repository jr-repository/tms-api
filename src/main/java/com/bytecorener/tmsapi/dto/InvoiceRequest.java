package com.bytecorener.tmsapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record InvoiceRequest(
        @NotNull(message = "Shipment order ID is required")
        Long shipmentOrderId,

        @NotNull(message = "Total amount is required")
        @Positive(message = "Total amount must be greater than zero")
        BigDecimal totalAmount
) {
}
