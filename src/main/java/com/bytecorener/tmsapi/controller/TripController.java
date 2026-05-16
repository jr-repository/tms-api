package com.bytecorener.tmsapi.controller;

import com.bytecorener.tmsapi.dto.*;
import com.bytecorener.tmsapi.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','DISPATCHER')")
public class TripController {
    private final TripService tripService;

    @PostMapping
    public ResponseEntity<ApiResponse<TripResponse>> createPlan(@Valid @RequestBody TripRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Trip plan created successfully", tripService.createPlan(request)));
    }

    @PostMapping("/{id}/assignment")
    public ResponseEntity<ApiResponse<TripResponse>> assign(@PathVariable Long id, @Valid @RequestBody AssignmentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Driver and vehicle assigned successfully", tripService.assign(id, request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TripResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success("Trips retrieved successfully", tripService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TripResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Trip retrieved successfully", tripService.findById(id)));
    }
}
