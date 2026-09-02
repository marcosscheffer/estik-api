package com.marcos.estik.domain.dto.user;

import com.marcos.estik.domain.enums.RoleEnum;

public record UserSummaryDTO(
    Long id,
    String username,
    RoleEnum role
) {
    
}
