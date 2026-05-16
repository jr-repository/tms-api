package com.bytecorener.tmsapi.dto;

public record DashboardSummaryResponse(
        long totalUsers,
        long totalVehicles,
        long totalShipments,
        long activeTrips,
        long deliveredShipments,
        long issuedInvoices
) {
}
