package com.marcos.estik.domain.dto.user;

import jakarta.validation.constraints.NotNull;

public record UserUpdateActiveDTO(
    @NotNull
    Boolean active
) {
    
}
