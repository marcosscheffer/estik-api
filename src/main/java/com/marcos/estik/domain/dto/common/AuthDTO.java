package com.marcos.estik.domain.dto.common;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(
    @NotBlank
    String username,
    @NotBlank
    String password
) {
    
}
