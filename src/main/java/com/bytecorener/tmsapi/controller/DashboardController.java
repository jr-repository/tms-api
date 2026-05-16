package com.bytecorener.tmsapi.controller;

import com.bytecorener.tmsapi.dto.ApiResponse;
import com.bytecorener.tmsapi.dto.DashboardSummaryResponse;
import com.bytecorener.tmsapi.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','DISPATCHER')")
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>> summary() {
        return ResponseEntity.ok(ApiResponse.success("Dashboard summary retrieved successfully", dashboardService.summary()));
    }
}
