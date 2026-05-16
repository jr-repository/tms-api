package com.bytecorener.tmsapi.repository;

import com.bytecorener.tmsapi.entity.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<DriverProfile, Long> {
    Optional<DriverProfile> findByUserId(Long userId);
    boolean existsByLicenseNumber(String licenseNumber);
}
