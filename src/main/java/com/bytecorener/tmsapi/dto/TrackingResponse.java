package com.bytecorener.tmsapi.dto;

import com.bytecorener.tmsapi.enumtype.ShipmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public record TrackingResponse(
        Long shipmentOrderId,
        String orderNumber,
        ShipmentStatus currentStatus,
        List<TrackingHistoryResponse> histories
) {
    public record TrackingHistoryResponse(ShipmentStatus status, String note, LocalDateTime createdAt) {
    }
}
