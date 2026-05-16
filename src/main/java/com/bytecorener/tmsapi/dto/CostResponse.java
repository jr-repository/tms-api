package com.bytecorener.tmsapi.dto;

import java.math.BigDecimal;

public record CostResponse(
        Long id,
        Long shipmentOrderId,
        String description,
        BigDecimal amount
) {
}
