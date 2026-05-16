package com.bytecorener.tmsapi.dto;

import com.bytecorener.tmsapi.enumtype.Role;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        Role role,
        boolean active
) {
}
