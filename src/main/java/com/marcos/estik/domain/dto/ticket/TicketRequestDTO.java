package com.marcos.estik.domain.dto.ticket;

import com.marcos.estik.domain.enums.PriorityEnum;
import com.marcos.estik.domain.enums.StatusEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketRequestDTO(
    @NotNull
    Long facilityId,
    @NotBlank
    String title,
    @NotBlank
    String description,
    StatusEnum status,
    @NotNull 
    PriorityEnum priority
) {
}
