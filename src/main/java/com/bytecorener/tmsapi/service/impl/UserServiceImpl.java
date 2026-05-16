package com.bytecorener.tmsapi.service.impl;

import com.bytecorener.tmsapi.dto.UserRequest;
import com.bytecorener.tmsapi.dto.UserResponse;
import com.bytecorener.tmsapi.entity.User;
import com.bytecorener.tmsapi.exception.BadRequestException;
import com.bytecorener.tmsapi.exception.ResourceNotFoundException;
import com.bytecorener.tmsapi.mapper.TmsMapper;
import com.bytecorener.tmsapi.repository.UserRepository;
import com.bytecorener.tmsapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TmsMapper mapper;

    @Override
    public UserResponse create(UserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Email is already registered");
        }
        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .active(request.active() == null || request.active())
                .build();
        return mapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(mapper::toUserResponse).toList();
    }

    @Override
    public UserResponse findById(Long id) {
        return mapper.toUserResponse(findUser(id));
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        User user = findUser(id);
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        user.setRole(request.role());
        user.setActive(request.active() == null || request.active());
        return mapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(findUser(id));
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
