package com.bytecorener.tmsapi.controller;

import com.bytecorener.tmsapi.dto.*;
import com.bytecorener.tmsapi.service.CostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/costs")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','DISPATCHER')")
public class CostController {
    private final CostService costService;

    @PostMapping
    public ResponseEntity<ApiResponse<CostResponse>> create(@Valid @RequestBody CostRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cost created successfully", costService.create(request)));
    }

    @GetMapping("/shipment/{shipmentOrderId}")
    public ResponseEntity<ApiResponse<List<CostResponse>>> findByShipment(@PathVariable Long shipmentOrderId) {
        return ResponseEntity.ok(ApiResponse.success("Costs retrieved successfully", costService.findByShipment(shipmentOrderId)));
    }
}
