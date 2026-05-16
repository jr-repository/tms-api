package com.bytecorener.tmsapi.service;

import com.bytecorener.tmsapi.dto.UserRequest;
import com.bytecorener.tmsapi.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create(UserRequest request);
    List<UserResponse> findAll();
    UserResponse findById(Long id);
    UserResponse update(Long id, UserRequest request);
    void delete(Long id);
}
