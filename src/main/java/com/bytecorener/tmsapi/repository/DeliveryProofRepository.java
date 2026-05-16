package com.bytecorener.tmsapi.repository;

import com.bytecorener.tmsapi.entity.DeliveryProof;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryProofRepository extends JpaRepository<DeliveryProof, Long> {
    Optional<DeliveryProof> findByShipmentOrderId(Long shipmentOrderId);
}
