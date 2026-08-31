package com.marcos.estik.domain.dto;

import com.marcos.estik.domain.enums.OsEnum;
import com.marcos.estik.domain.enums.StorageEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PcRequestDTO(
    @NotBlank
    String name,
    @NotBlank
    String processor,
    @NotBlank
    String memory,
    @NotNull
    StorageEnum storageType,
    @NotNull
    Integer storageCapacity,
    @NotNull
    Long facilityId,
    @NotNull
    Long assemblerId,
    @NotNull
    OsEnum os
) {
    
}
