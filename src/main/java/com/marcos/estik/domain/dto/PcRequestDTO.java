package com.marcos.estik.domain.dto;

import com.marcos.estik.domain.enums.StorageEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PcRequestDTO(
    @NotBlank
    String name,
    @NotNull
    Long assembler,
    @NotBlank
    String processor,
    @NotBlank
    String memory,
    @NotBlank
    StorageEnum storageType,
    @NotBlank
    Integer storageCapacity,
    @NotNull
    Long facilityId
) {
    
}
