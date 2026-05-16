package com.bytecorener.tmsapi.service;

import com.bytecorener.tmsapi.dto.DriverRequest;
import com.bytecorener.tmsapi.dto.DriverResponse;

import java.util.List;

public interface DriverService {
    DriverResponse create(DriverRequest request);
    List<DriverResponse> findAll();
    DriverResponse findById(Long id);
    DriverResponse update(Long id, DriverRequest request);
}
