package com.bytecorener.tmsapi.entity;

import com.bytecorener.tmsapi.enumtype.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipment_orders")
public class ShipmentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String orderNumber;
    @ManyToOne(optional = false)
    private CustomerProfile customer;
    @Column(nullable = false)
    private String pickupAddress;
    @Column(nullable = false)
    private String deliveryAddress;
    @Column(nullable = false)
    private String cargoDescription;
    private Double weightKg;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
