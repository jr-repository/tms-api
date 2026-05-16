package com.bytecorener.tmsapi.entity;

import com.bytecorener.tmsapi.enumtype.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String plateNumber;
    @Column(nullable = false)
    private String vehicleType;
    private Double capacityKg;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status;
}
