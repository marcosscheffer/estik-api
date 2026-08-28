package com.marcos.estik.domain.dto;


public record ItemResponseDTO(
    Long id,
    String name,
    String description,
    String code,
    Integer quantity
) {

}
