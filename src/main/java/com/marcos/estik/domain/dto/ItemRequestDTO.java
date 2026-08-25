package com.marcos.estik.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record ItemRequestDTO(
    @NotBlank
    String name,
    @NotBlank
    String code,
    String description
) {
    
}
