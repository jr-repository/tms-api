package com.bytecorener.tmsapi.service;

import com.bytecorener.tmsapi.dto.AssignmentRequest;
import com.bytecorener.tmsapi.dto.TripRequest;
import com.bytecorener.tmsapi.dto.TripResponse;

import java.util.List;

public interface TripService {
    TripResponse createPlan(TripRequest request);
    TripResponse assign(Long tripId, AssignmentRequest request);
    List<TripResponse> findAll();
    TripResponse findById(Long id);
}
