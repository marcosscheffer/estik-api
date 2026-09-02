package com.marcos.estik.domain.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketRequestDTO(
    @NotNull
    Long facilityId,
    @NotBlank
    String title,
    @NotBlank
    String description
) {
}
