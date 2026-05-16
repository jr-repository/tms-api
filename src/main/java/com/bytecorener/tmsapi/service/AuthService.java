package com.bytecorener.tmsapi.service;

import com.bytecorener.tmsapi.dto.AuthResponse;
import com.bytecorener.tmsapi.dto.LoginRequest;
import com.bytecorener.tmsapi.dto.UserRequest;
import com.bytecorener.tmsapi.dto.UserResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    UserResponse register(UserRequest request);
}
