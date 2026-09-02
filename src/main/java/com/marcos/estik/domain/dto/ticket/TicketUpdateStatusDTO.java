package com.marcos.estik.domain.dto.ticket;

import com.marcos.estik.domain.enums.StatusEnum;

import jakarta.validation.constraints.NotNull;

public record TicketUpdateStatusDTO(
    @NotNull
    StatusEnum status
) {
    
}
