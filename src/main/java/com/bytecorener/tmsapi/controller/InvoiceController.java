package com.bytecorener.tmsapi.controller;

import com.bytecorener.tmsapi.dto.*;
import com.bytecorener.tmsapi.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER')")
    public ResponseEntity<ApiResponse<InvoiceResponse>> create(@Valid @RequestBody InvoiceRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Invoice created successfully", invoiceService.create(request)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER','CUSTOMER')")
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success("Invoices retrieved successfully", invoiceService.findAll()));
    }

    @PostMapping("/{id}/paid")
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER')")
    public ResponseEntity<ApiResponse<InvoiceResponse>> markPaid(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Invoice marked as paid successfully", invoiceService.markPaid(id)));
    }
}
