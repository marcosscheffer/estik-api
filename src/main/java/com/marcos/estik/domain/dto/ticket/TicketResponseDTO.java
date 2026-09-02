package com.marcos.estik.domain.dto.ticket;

import java.time.LocalDateTime;

import com.marcos.estik.domain.dto.facility.FacilitySummaryResponseDTO;
import com.marcos.estik.domain.dto.user.UserSummaryDTO;
import com.marcos.estik.domain.enums.StatusEnum;

public record TicketResponseDTO(
    Long id,
    StatusEnum status,
    String title,
    String description,
    UserSummaryDTO user,
    FacilitySummaryResponseDTO facility,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    
}
