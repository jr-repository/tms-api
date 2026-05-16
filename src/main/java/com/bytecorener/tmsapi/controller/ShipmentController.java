package com.bytecorener.tmsapi.controller;

import com.bytecorener.tmsapi.dto.*;
import com.bytecorener.tmsapi.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
public class ShipmentController {
    private final ShipmentService shipmentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER','CUSTOMER')")
    public ResponseEntity<ApiResponse<ShipmentOrderResponse>> create(@Valid @RequestBody ShipmentOrderRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Shipment order created successfully", shipmentService.create(request)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER')")
    public ResponseEntity<ApiResponse<List<ShipmentOrderResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success("Shipment orders retrieved successfully", shipmentService.findAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER','DRIVER','CUSTOMER')")
    public ResponseEntity<ApiResponse<ShipmentOrderResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Shipment order retrieved successfully", shipmentService.findById(id)));
    }

    @PostMapping("/{id}/pickup-confirmation")
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER','DRIVER')")
    public ResponseEntity<ApiResponse<ShipmentOrderResponse>> confirmPickup(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Pickup confirmed successfully", shipmentService.confirmPickup(id, request)));
    }

    @PostMapping("/{id}/delivery-confirmation")
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER','DRIVER')")
    public ResponseEntity<ApiResponse<ShipmentOrderResponse>> confirmDelivery(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Delivery confirmed successfully", shipmentService.confirmDelivery(id, request)));
    }

    @GetMapping("/{id}/tracking")
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER','DRIVER','CUSTOMER')")
    public ResponseEntity<ApiResponse<TrackingResponse>> track(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Shipment tracking retrieved successfully", shipmentService.track(id)));
    }

    @PostMapping("/{id}/delivery-proof")
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER','DRIVER')")
    public ResponseEntity<ApiResponse<Void>> uploadDeliveryProof(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        shipmentService.uploadDeliveryProof(id, file);
        return ResponseEntity.ok(ApiResponse.success("Delivery proof uploaded successfully", null));
    }
}
