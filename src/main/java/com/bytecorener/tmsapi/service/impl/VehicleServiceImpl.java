package com.bytecorener.tmsapi.service.impl;

import com.bytecorener.tmsapi.dto.VehicleRequest;
import com.bytecorener.tmsapi.dto.VehicleResponse;
import com.bytecorener.tmsapi.entity.Vehicle;
import com.bytecorener.tmsapi.exception.BadRequestException;
import com.bytecorener.tmsapi.exception.ResourceNotFoundException;
import com.bytecorener.tmsapi.mapper.TmsMapper;
import com.bytecorener.tmsapi.repository.VehicleRepository;
import com.bytecorener.tmsapi.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final TmsMapper mapper;

    @Override
    public VehicleResponse create(VehicleRequest request) {
        if (vehicleRepository.existsByPlateNumber(request.plateNumber())) {
            throw new BadRequestException("Plate number is already registered");
        }
        Vehicle vehicle = Vehicle.builder().plateNumber(request.plateNumber()).vehicleType(request.vehicleType()).capacityKg(request.capacityKg()).status(request.status()).build();
        return mapper.toVehicleResponse(vehicleRepository.save(vehicle));
    }

    @Override
    public List<VehicleResponse> findAll() {
        return vehicleRepository.findAll().stream().map(mapper::toVehicleResponse).toList();
    }

    @Override
    public VehicleResponse findById(Long id) {
        return mapper.toVehicleResponse(findVehicle(id));
    }

    @Override
    public VehicleResponse update(Long id, VehicleRequest request) {
        Vehicle vehicle = findVehicle(id);
        vehicle.setPlateNumber(request.plateNumber());
        vehicle.setVehicleType(request.vehicleType());
        vehicle.setCapacityKg(request.capacityKg());
        vehicle.setStatus(request.status());
        return mapper.toVehicleResponse(vehicleRepository.save(vehicle));
    }

    @Override
    public void delete(Long id) {
        vehicleRepository.delete(findVehicle(id));
    }

    private Vehicle findVehicle(Long id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
    }
}
