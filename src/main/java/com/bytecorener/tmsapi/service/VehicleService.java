package com.bytecorener.tmsapi.service;

import com.bytecorener.tmsapi.dto.VehicleRequest;
import com.bytecorener.tmsapi.dto.VehicleResponse;

import java.util.List;

public interface VehicleService {
    VehicleResponse create(VehicleRequest request);
    List<VehicleResponse> findAll();
    VehicleResponse findById(Long id);
    VehicleResponse update(Long id, VehicleRequest request);
    void delete(Long id);
}
