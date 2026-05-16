package com.bytecorener.tmsapi.service.impl;

import com.bytecorener.tmsapi.dto.DashboardSummaryResponse;
import com.bytecorener.tmsapi.enumtype.InvoiceStatus;
import com.bytecorener.tmsapi.enumtype.ShipmentStatus;
import com.bytecorener.tmsapi.enumtype.TripStatus;
import com.bytecorener.tmsapi.repository.*;
import com.bytecorener.tmsapi.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ShipmentOrderRepository shipmentOrderRepository;
    private final TripRepository tripRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    public DashboardSummaryResponse summary() {
        return new DashboardSummaryResponse(
                userRepository.count(),
                vehicleRepository.count(),
                shipmentOrderRepository.count(),
                tripRepository.countByStatus(TripStatus.ACTIVE),
                shipmentOrderRepository.countByStatus(ShipmentStatus.DELIVERED),
                invoiceRepository.countByStatus(InvoiceStatus.ISSUED)
        );
    }
}
