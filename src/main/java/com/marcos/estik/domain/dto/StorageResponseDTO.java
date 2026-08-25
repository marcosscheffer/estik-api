package com.marcos.estik.domain.dto;

public record StorageResponseDTO(
    Long id,
    ItemResponseDTO item,
    String code,
    Integer quantity
) {
    
}
