package com.marcos.estik.domain.dto;

import java.time.LocalDateTime;

public record HeatbeatDTO(
    String ip,
    LocalDateTime lastPing
) {
    
}
