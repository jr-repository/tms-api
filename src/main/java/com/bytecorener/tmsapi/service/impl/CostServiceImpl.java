package com.bytecorener.tmsapi.service.impl;

import com.bytecorener.tmsapi.dto.CostRequest;
import com.bytecorener.tmsapi.dto.CostResponse;
import com.bytecorener.tmsapi.entity.Cost;
import com.bytecorener.tmsapi.exception.ResourceNotFoundException;
import com.bytecorener.tmsapi.mapper.TmsMapper;
import com.bytecorener.tmsapi.repository.CostRepository;
import com.bytecorener.tmsapi.repository.ShipmentOrderRepository;
import com.bytecorener.tmsapi.service.CostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CostServiceImpl implements CostService {
    private final CostRepository costRepository;
    private final ShipmentOrderRepository shipmentOrderRepository;
    private final TmsMapper mapper;

    @Override
    public CostResponse create(CostRequest request) {
        var shipment = shipmentOrderRepository.findById(request.shipmentOrderId()).orElseThrow(() -> new ResourceNotFoundException("Shipment order not found"));
        Cost cost = Cost.builder().shipmentOrder(shipment).description(request.description()).amount(request.amount()).build();
        return mapper.toCostResponse(costRepository.save(cost));
    }

    @Override
    public List<CostResponse> findByShipment(Long shipmentOrderId) {
        return costRepository.findByShipmentOrderId(shipmentOrderId).stream().map(mapper::toCostResponse).toList();
    }
}
