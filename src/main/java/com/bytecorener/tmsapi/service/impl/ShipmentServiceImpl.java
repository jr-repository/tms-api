package com.bytecorener.tmsapi.service.impl;

import com.bytecorener.tmsapi.dto.*;
import com.bytecorener.tmsapi.entity.*;
import com.bytecorener.tmsapi.enumtype.ShipmentStatus;
import com.bytecorener.tmsapi.exception.BadRequestException;
import com.bytecorener.tmsapi.exception.ResourceNotFoundException;
import com.bytecorener.tmsapi.mapper.TmsMapper;
import com.bytecorener.tmsapi.queue.NotificationQueueProducer;
import com.bytecorener.tmsapi.repository.*;
import com.bytecorener.tmsapi.service.ShipmentService;
import com.bytecorener.tmsapi.websocket.LiveShipmentStatusPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentOrderRepository shipmentOrderRepository;
    private final CustomerRepository customerRepository;
    private final ShipmentStatusHistoryRepository historyRepository;
    private final DeliveryProofRepository deliveryProofRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final NotificationQueueProducer notificationQueueProducer;
    private final LiveShipmentStatusPublisher liveShipmentStatusPublisher;
    private final TmsMapper mapper;

    @Override
    public ShipmentOrderResponse create(ShipmentOrderRequest request) {
        CustomerProfile customer = customerRepository.findById(request.customerId()).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        ShipmentOrder order = ShipmentOrder.builder()
                .orderNumber("SHP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .customer(customer)
                .pickupAddress(request.pickupAddress())
                .deliveryAddress(request.deliveryAddress())
                .cargoDescription(request.cargoDescription())
                .weightKg(request.weightKg())
                .status(ShipmentStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        ShipmentOrder saved = shipmentOrderRepository.save(order);
        addHistory(saved, ShipmentStatus.CREATED, "Shipment order created");
        return mapper.toShipmentOrderResponse(saved);
    }

    @Override
    public List<ShipmentOrderResponse> findAll() {
        return shipmentOrderRepository.findAll().stream().map(mapper::toShipmentOrderResponse).toList();
    }

    @Override
    public ShipmentOrderResponse findById(Long id) {
        return mapper.toShipmentOrderResponse(findOrder(id));
    }

    @Override
    public ShipmentOrderResponse confirmPickup(Long id, StatusUpdateRequest request) {
        return updateStatus(id, ShipmentStatus.PICKED_UP, request.note());
    }

    @Override
    public ShipmentOrderResponse confirmDelivery(Long id, StatusUpdateRequest request) {
        return updateStatus(id, ShipmentStatus.DELIVERED, request.note());
    }

    @Override
    public TrackingResponse track(Long id) {
        String key = trackingKey(id);
        Object cached = redisTemplate.opsForValue().get(key);
        if (cached instanceof TrackingResponse trackingResponse) {
            return trackingResponse;
        }
        ShipmentOrder order = findOrder(id);
        List<TrackingResponse.TrackingHistoryResponse> histories = historyRepository.findByShipmentOrderIdOrderByCreatedAtAsc(id).stream()
                .map(history -> new TrackingResponse.TrackingHistoryResponse(history.getStatus(), history.getNote(), history.getCreatedAt()))
                .toList();
        TrackingResponse response = new TrackingResponse(order.getId(), order.getOrderNumber(), order.getStatus(), histories);
        redisTemplate.opsForValue().set(key, response, 10, TimeUnit.MINUTES);
        return response;
    }

    @Override
    public void uploadDeliveryProof(Long id, MultipartFile file) {
        ShipmentOrder order = findOrder(id);
        if (file.isEmpty()) {
            throw new BadRequestException("Delivery proof file is required");
        }
        try {
            DeliveryProof proof = DeliveryProof.builder()
                    .shipmentOrder(order)
                    .fileName(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .fileData(file.getBytes())
                    .uploadedAt(LocalDateTime.now())
                    .build();
            deliveryProofRepository.save(proof);
        } catch (IOException ex) {
            throw new BadRequestException("Failed to upload delivery proof");
        }
    }

    private ShipmentOrderResponse updateStatus(Long id, ShipmentStatus status, String note) {
        ShipmentOrder order = findOrder(id);
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        ShipmentOrder saved = shipmentOrderRepository.save(order);
        addHistory(saved, status, note);
        redisTemplate.delete(trackingKey(id));
        notificationQueueProducer.send(new NotificationMessage(saved.getId(), saved.getOrderNumber(), status.name(), "Shipment status updated"));
        liveShipmentStatusPublisher.publish(new LiveShipmentStatusMessage(saved.getId(), saved.getOrderNumber(), status.name(), note));
        return mapper.toShipmentOrderResponse(saved);
    }

    private void addHistory(ShipmentOrder order, ShipmentStatus status, String note) {
        historyRepository.save(ShipmentStatusHistory.builder().shipmentOrder(order).status(status).note(note).createdAt(LocalDateTime.now()).build());
    }

    private ShipmentOrder findOrder(Long id) {
        return shipmentOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Shipment order not found"));
    }

    private String trackingKey(Long id) {
        return "shipment:tracking:" + id;
    }
}
