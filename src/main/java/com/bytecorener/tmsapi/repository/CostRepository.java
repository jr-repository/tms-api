package com.bytecorener.tmsapi.repository;

import com.bytecorener.tmsapi.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CostRepository extends JpaRepository<Cost, Long> {
    List<Cost> findByShipmentOrderId(Long shipmentOrderId);
}
