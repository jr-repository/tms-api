package com.bytecorener.tmsapi.service.impl;

import com.bytecorener.tmsapi.dto.AuthResponse;
import com.bytecorener.tmsapi.dto.LoginRequest;
import com.bytecorener.tmsapi.dto.UserRequest;
import com.bytecorener.tmsapi.dto.UserResponse;
import com.bytecorener.tmsapi.mapper.TmsMapper;
import com.bytecorener.tmsapi.repository.UserRepository;
import com.bytecorener.tmsapi.security.JwtTokenProvider;
import com.bytecorener.tmsapi.service.AuthService;
import com.bytecorener.tmsapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;
    private final TmsMapper mapper;

    @Override
    public AuthResponse login(LoginRequest request) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        String token = jwtTokenProvider.generateToken(authentication);
        var user = userRepository.findByEmail(request.email()).orElseThrow();
        return new AuthResponse(token, "Bearer", mapper.toUserResponse(user));
    }

    @Override
    public UserResponse register(UserRequest request) {
        return userService.create(request);
    }
}
