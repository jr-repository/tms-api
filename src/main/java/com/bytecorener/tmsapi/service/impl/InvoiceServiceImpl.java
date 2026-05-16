package com.bytecorener.tmsapi.service.impl;

import com.bytecorener.tmsapi.dto.InvoiceRequest;
import com.bytecorener.tmsapi.dto.InvoiceResponse;
import com.bytecorener.tmsapi.entity.Invoice;
import com.bytecorener.tmsapi.enumtype.InvoiceStatus;
import com.bytecorener.tmsapi.exception.ResourceNotFoundException;
import com.bytecorener.tmsapi.mapper.TmsMapper;
import com.bytecorener.tmsapi.repository.InvoiceRepository;
import com.bytecorener.tmsapi.repository.ShipmentOrderRepository;
import com.bytecorener.tmsapi.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ShipmentOrderRepository shipmentOrderRepository;
    private final TmsMapper mapper;

    @Override
    public InvoiceResponse create(InvoiceRequest request) {
        var shipment = shipmentOrderRepository.findById(request.shipmentOrderId()).orElseThrow(() -> new ResourceNotFoundException("Shipment order not found"));
        Invoice invoice = Invoice.builder()
                .invoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .shipmentOrder(shipment)
                .totalAmount(request.totalAmount())
                .status(InvoiceStatus.ISSUED)
                .issuedAt(LocalDateTime.now())
                .build();
        return mapper.toInvoiceResponse(invoiceRepository.save(invoice));
    }

    @Override
    public List<InvoiceResponse> findAll() {
        return invoiceRepository.findAll().stream().map(mapper::toInvoiceResponse).toList();
    }

    @Override
    public InvoiceResponse markPaid(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
        invoice.setStatus(InvoiceStatus.PAID);
        return mapper.toInvoiceResponse(invoiceRepository.save(invoice));
    }
}
