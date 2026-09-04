package com.marcos.estik.domain.dto.item;

public record ItemDepartamentResponseDTO(
    Long id,
    Integer quantity,
    ItemSummaryResponseDTO item
) {
    
}
