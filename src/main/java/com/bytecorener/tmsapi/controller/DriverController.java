package com.bytecorener.tmsapi.controller;

import com.bytecorener.tmsapi.dto.*;
import com.bytecorener.tmsapi.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','DISPATCHER')")
public class DriverController {
    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<ApiResponse<DriverResponse>> create(@Valid @RequestBody DriverRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Driver created successfully", driverService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DriverResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success("Drivers retrieved successfully", driverService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DriverResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Driver retrieved successfully", driverService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DriverResponse>> update(@PathVariable Long id, @Valid @RequestBody DriverRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Driver updated successfully", driverService.update(id, request)));
    }
}
