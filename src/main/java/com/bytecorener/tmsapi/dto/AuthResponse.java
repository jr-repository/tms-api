package com.bytecorener.tmsapi.dto;

public record AuthResponse(
        String token,
        String tokenType,
        UserResponse user
) {
}
