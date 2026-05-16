package com.bytecorener.tmsapi.repository;

import com.bytecorener.tmsapi.entity.Trip;
import com.bytecorener.tmsapi.enumtype.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
    long countByStatus(TripStatus status);
}
