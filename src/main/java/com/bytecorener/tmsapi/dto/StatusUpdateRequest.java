package com.bytecorener.tmsapi.dto;

import jakarta.validation.constraints.NotBlank;

public record StatusUpdateRequest(
        @NotBlank(message = "Note is required")
        String note
) {
}
