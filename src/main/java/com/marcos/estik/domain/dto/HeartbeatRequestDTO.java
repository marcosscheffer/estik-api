package com.marcos.estik.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record HeartbeatRequestDTO(
    @NotBlank
    String name,
    @NotBlank
    String ip
) {
    
}
