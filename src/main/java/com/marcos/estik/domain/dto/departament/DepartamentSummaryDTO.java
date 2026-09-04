package com.marcos.estik.domain.dto.departament;

import com.marcos.estik.domain.dto.facility.FacilitySummaryResponseDTO;

public record DepartamentSummaryDTO(
    Long id,
    String name,
    FacilitySummaryResponseDTO facility
) {
} 
