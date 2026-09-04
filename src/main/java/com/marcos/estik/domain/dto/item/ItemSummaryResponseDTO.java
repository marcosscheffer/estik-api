package com.marcos.estik.domain.dto.item;

public record ItemSummaryResponseDTO(
    Long id,
    String name,
    String description,
    String code
) {
    
}
