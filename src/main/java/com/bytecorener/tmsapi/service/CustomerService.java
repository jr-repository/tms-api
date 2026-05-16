package com.bytecorener.tmsapi.service;

import com.bytecorener.tmsapi.dto.CustomerRequest;
import com.bytecorener.tmsapi.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest request);
    List<CustomerResponse> findAll();
    CustomerResponse findById(Long id);
    CustomerResponse update(Long id, CustomerRequest request);
}
