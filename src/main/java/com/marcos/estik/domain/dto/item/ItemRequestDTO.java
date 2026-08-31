package com.marcos.estik.domain.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemRequestDTO(
    @NotBlank
    String name,
    @NotBlank
    String code,
    String description,
    @NotNull
    Integer quantity
) {
    
}
