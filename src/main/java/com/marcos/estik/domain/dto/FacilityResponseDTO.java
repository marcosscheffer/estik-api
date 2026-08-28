package com.marcos.estik.domain.dto;

import java.util.List;

public record FacilityResponseDTO(
    Long id,
    String name,
    String code,
    List<PcSummaryResponseDTO> pcs
) {
    
}
