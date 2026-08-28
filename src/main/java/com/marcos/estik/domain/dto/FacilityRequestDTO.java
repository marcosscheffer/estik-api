package com.marcos.estik.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record FacilityRequestDTO(
    @NotBlank
    String name,
    @NotBlank
    String code
) {
    
}
