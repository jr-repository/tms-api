package com.bytecorener.tmsapi.service;

import com.bytecorener.tmsapi.dto.CostRequest;
import com.bytecorener.tmsapi.dto.CostResponse;

import java.util.List;

public interface CostService {
    CostResponse create(CostRequest request);
    List<CostResponse> findByShipment(Long shipmentOrderId);
}
