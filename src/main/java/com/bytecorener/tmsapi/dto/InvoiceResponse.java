package com.bytecorener.tmsapi.dto;

import com.bytecorener.tmsapi.enumtype.InvoiceStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InvoiceResponse(
        Long id,
        String invoiceNumber,
        Long shipmentOrderId,
        BigDecimal totalAmount,
        InvoiceStatus status,
        LocalDateTime issuedAt
) {
}
