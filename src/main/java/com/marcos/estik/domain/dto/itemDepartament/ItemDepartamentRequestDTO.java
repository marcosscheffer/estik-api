package com.marcos.estik.domain.dto.itemDepartament;

import jakarta.validation.constraints.NotNull;

public record ItemDepartamentRequestDTO(
    @NotNull
    Integer quantity,
    @NotNull
    Long departamentId,
    @NotNull
    Long itemId
) {
} 
