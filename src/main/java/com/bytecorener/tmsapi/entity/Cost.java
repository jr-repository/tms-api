package com.bytecorener.tmsapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "costs")
public class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private ShipmentOrder shipmentOrder;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal amount;
}
