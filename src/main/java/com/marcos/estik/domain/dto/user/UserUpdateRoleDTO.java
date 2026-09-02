package com.marcos.estik.domain.dto.user;

import com.marcos.estik.domain.enums.RoleEnum;

import jakarta.validation.constraints.NotNull;

public record UserUpdateRoleDTO(
    @NotNull
    RoleEnum role
) {
    
}
