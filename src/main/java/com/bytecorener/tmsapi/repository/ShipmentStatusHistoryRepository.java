package com.bytecorener.tmsapi.repository;

import com.bytecorener.tmsapi.entity.ShipmentStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentStatusHistoryRepository extends JpaRepository<ShipmentStatusHistory, Long> {
    List<ShipmentStatusHistory> findByShipmentOrderIdOrderByCreatedAtAsc(Long shipmentOrderId);
}
