package com.marcos.estik.domain.dto.facility;

import java.util.List;

import com.marcos.estik.domain.dto.pc.PcSummaryResponseDTO;

public record FacilityResponseDTO(
    Long id,
    String name,
    String code,
    List<PcSummaryResponseDTO> pcs
) {
    
}
