package com.marcos.estik.domain.dto.departament;

import jakarta.validation.constraints.NotNull;

public record DepartamentRequestDTO (
    @NotNull
    String name,
    @NotNull
    Long facilityId
) {
    
}
