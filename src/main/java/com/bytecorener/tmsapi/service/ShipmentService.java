package com.bytecorener.tmsapi.service;

import com.bytecorener.tmsapi.dto.ShipmentOrderRequest;
import com.bytecorener.tmsapi.dto.ShipmentOrderResponse;
import com.bytecorener.tmsapi.dto.StatusUpdateRequest;
import com.bytecorener.tmsapi.dto.TrackingResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShipmentService {
    ShipmentOrderResponse create(ShipmentOrderRequest request);
    List<ShipmentOrderResponse> findAll();
    ShipmentOrderResponse findById(Long id);
    ShipmentOrderResponse confirmPickup(Long id, StatusUpdateRequest request);
    ShipmentOrderResponse confirmDelivery(Long id, StatusUpdateRequest request);
    TrackingResponse track(Long id);
    void uploadDeliveryProof(Long id, MultipartFile file);
}
