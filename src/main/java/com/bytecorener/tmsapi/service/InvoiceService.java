package com.bytecorener.tmsapi.service;

import com.bytecorener.tmsapi.dto.InvoiceRequest;
import com.bytecorener.tmsapi.dto.InvoiceResponse;

import java.util.List;

public interface InvoiceService {
    InvoiceResponse create(InvoiceRequest request);
    List<InvoiceResponse> findAll();
    InvoiceResponse markPaid(Long id);
}
