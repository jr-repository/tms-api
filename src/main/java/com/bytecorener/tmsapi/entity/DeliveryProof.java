package com.bytecorener.tmsapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "delivery_proofs")
public class DeliveryProof {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(optional = false)
    private ShipmentOrder shipmentOrder;
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private String contentType;
    @Lob
    @Column(nullable = false)
    private byte[] fileData;
    private LocalDateTime uploadedAt;
}
