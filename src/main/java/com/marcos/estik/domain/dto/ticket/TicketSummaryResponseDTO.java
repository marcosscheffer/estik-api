package com.marcos.estik.domain.dto.ticket;

import java.time.LocalDateTime;

import com.marcos.estik.domain.dto.user.UserSummaryDTO;
import com.marcos.estik.domain.enums.PriorityEnum;
import com.marcos.estik.domain.enums.StatusEnum;

public record TicketSummaryResponseDTO(
    Long id,
    StatusEnum status,
    String title,
    UserSummaryDTO user,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    PriorityEnum priority
) {
}
