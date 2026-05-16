package com.bytecorener.tmsapi.service.impl;

import com.bytecorener.tmsapi.dto.DriverRequest;
import com.bytecorener.tmsapi.dto.DriverResponse;
import com.bytecorener.tmsapi.entity.DriverProfile;
import com.bytecorener.tmsapi.exception.BadRequestException;
import com.bytecorener.tmsapi.exception.ResourceNotFoundException;
import com.bytecorener.tmsapi.mapper.TmsMapper;
import com.bytecorener.tmsapi.repository.DriverRepository;
import com.bytecorener.tmsapi.repository.UserRepository;
import com.bytecorener.tmsapi.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    private final TmsMapper mapper;

    @Override
    public DriverResponse create(DriverRequest request) {
        if (driverRepository.existsByLicenseNumber(request.licenseNumber())) {
            throw new BadRequestException("License number is already registered");
        }
        var user = userRepository.findById(request.userId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        DriverProfile driver = DriverProfile.builder().user(user).licenseNumber(request.licenseNumber()).phoneNumber(request.phoneNumber()).available(request.available() == null || request.available()).build();
        return mapper.toDriverResponse(driverRepository.save(driver));
    }

    @Override
    public List<DriverResponse> findAll() {
        return driverRepository.findAll().stream().map(mapper::toDriverResponse).toList();
    }

    @Override
    public DriverResponse findById(Long id) {
        return mapper.toDriverResponse(findDriver(id));
    }

    @Override
    public DriverResponse update(Long id, DriverRequest request) {
        DriverProfile driver = findDriver(id);
        driver.setLicenseNumber(request.licenseNumber());
        driver.setPhoneNumber(request.phoneNumber());
        driver.setAvailable(request.available() == null || request.available());
        return mapper.toDriverResponse(driverRepository.save(driver));
    }

    private DriverProfile findDriver(Long id) {
        return driverRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
    }
}
