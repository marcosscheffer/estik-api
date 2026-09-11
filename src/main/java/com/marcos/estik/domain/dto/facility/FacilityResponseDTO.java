package com.marcos.estik.domain.dto.facility;

import java.util.List;

import com.marcos.estik.domain.dto.departament.DepartamentSummaryDTO;
import com.marcos.estik.domain.dto.ticket.TicketSummaryResponseDTO;

public record FacilityResponseDTO(
    Long id,
    String name,
    String code,
    List<DepartamentSummaryDTO> departaments,
    List<TicketSummaryResponseDTO> tickets
) {
    
}
