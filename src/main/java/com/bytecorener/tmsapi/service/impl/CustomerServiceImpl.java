package com.bytecorener.tmsapi.service.impl;

import com.bytecorener.tmsapi.dto.CustomerRequest;
import com.bytecorener.tmsapi.dto.CustomerResponse;
import com.bytecorener.tmsapi.entity.CustomerProfile;
import com.bytecorener.tmsapi.exception.ResourceNotFoundException;
import com.bytecorener.tmsapi.mapper.TmsMapper;
import com.bytecorener.tmsapi.repository.CustomerRepository;
import com.bytecorener.tmsapi.repository.UserRepository;
import com.bytecorener.tmsapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final TmsMapper mapper;

    @Override
    public CustomerResponse create(CustomerRequest request) {
        var user = userRepository.findById(request.userId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        CustomerProfile customer = CustomerProfile.builder().user(user).companyName(request.companyName()).phoneNumber(request.phoneNumber()).address(request.address()).build();
        return mapper.toCustomerResponse(customerRepository.save(customer));
    }

    @Override
    public List<CustomerResponse> findAll() {
        return customerRepository.findAll().stream().map(mapper::toCustomerResponse).toList();
    }

    @Override
    public CustomerResponse findById(Long id) {
        return mapper.toCustomerResponse(findCustomer(id));
    }

    @Override
    public CustomerResponse update(Long id, CustomerRequest request) {
        CustomerProfile customer = findCustomer(id);
        customer.setCompanyName(request.companyName());
        customer.setPhoneNumber(request.phoneNumber());
        customer.setAddress(request.address());
        return mapper.toCustomerResponse(customerRepository.save(customer));
    }

    private CustomerProfile findCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }
}
