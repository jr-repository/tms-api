package com.bytecorener.tmsapi.repository;

import com.bytecorener.tmsapi.entity.ShipmentOrder;
import com.bytecorener.tmsapi.enumtype.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentOrderRepository extends JpaRepository<ShipmentOrder, Long> {
    Optional<ShipmentOrder> findByOrderNumber(String orderNumber);
    long countByStatus(ShipmentStatus status);
}
