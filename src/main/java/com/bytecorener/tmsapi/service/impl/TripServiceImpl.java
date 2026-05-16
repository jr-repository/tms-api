package com.bytecorener.tmsapi.service.impl;

import com.bytecorener.tmsapi.dto.AssignmentRequest;
import com.bytecorener.tmsapi.dto.TripRequest;
import com.bytecorener.tmsapi.dto.TripResponse;
import com.bytecorener.tmsapi.entity.Trip;
import com.bytecorener.tmsapi.enumtype.ShipmentStatus;
import com.bytecorener.tmsapi.enumtype.TripStatus;
import com.bytecorener.tmsapi.enumtype.VehicleStatus;
import com.bytecorener.tmsapi.exception.ResourceNotFoundException;
import com.bytecorener.tmsapi.mapper.TmsMapper;
import com.bytecorener.tmsapi.repository.*;
import com.bytecorener.tmsapi.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final ShipmentOrderRepository shipmentOrderRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final TmsMapper mapper;

    @Override
    public TripResponse createPlan(TripRequest request) {
        var shipment = shipmentOrderRepository.findById(request.shipmentOrderId()).orElseThrow(() -> new ResourceNotFoundException("Shipment order not found"));
        shipment.setStatus(ShipmentStatus.PLANNED);
        shipment.setUpdatedAt(LocalDateTime.now());
        shipmentOrderRepository.save(shipment);
        Trip trip = Trip.builder().shipmentOrder(shipment).plannedPickupAt(request.plannedPickupAt()).plannedDeliveryAt(request.plannedDeliveryAt()).status(TripStatus.PLANNED).build();
        return mapper.toTripResponse(tripRepository.save(trip));
    }

    @Override
    public TripResponse assign(Long tripId, AssignmentRequest request) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new ResourceNotFoundException("Trip not found"));
        var driver = driverRepository.findById(request.driverId()).orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
        var vehicle = vehicleRepository.findById(request.vehicleId()).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        trip.setDriver(driver);
        trip.setVehicle(vehicle);
        trip.setStatus(TripStatus.ACTIVE);
        trip.getShipmentOrder().setStatus(ShipmentStatus.ASSIGNED);
        trip.getShipmentOrder().setUpdatedAt(LocalDateTime.now());
        driver.setAvailable(false);
        vehicle.setStatus(VehicleStatus.ASSIGNED);
        driverRepository.save(driver);
        vehicleRepository.save(vehicle);
        shipmentOrderRepository.save(trip.getShipmentOrder());
        return mapper.toTripResponse(tripRepository.save(trip));
    }

    @Override
    public List<TripResponse> findAll() {
        return tripRepository.findAll().stream().map(mapper::toTripResponse).toList();
    }

    @Override
    public TripResponse findById(Long id) {
        return mapper.toTripResponse(tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trip not found")));
    }
}
