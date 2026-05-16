package com.bytecorener.tmsapi.controller;

import com.bytecorener.tmsapi.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','DISPATCHER')")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/shipments/{shipmentOrderId}/pdf")
    public ResponseEntity<byte[]> shipmentReport(@PathVariable Long shipmentOrderId) {
        byte[] pdf = reportService.shipmentReport(shipmentOrderId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=shipment-report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
