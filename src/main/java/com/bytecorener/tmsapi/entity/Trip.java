package com.bytecorener.tmsapi.entity;

import com.bytecorener.tmsapi.enumtype.TripStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(optional = false)
    private ShipmentOrder shipmentOrder;
    @ManyToOne
    private DriverProfile driver;
    @ManyToOne
    private Vehicle vehicle;
    private LocalDateTime plannedPickupAt;
    private LocalDateTime plannedDeliveryAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status;
}
