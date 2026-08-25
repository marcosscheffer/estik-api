package com.marcos.estik.domain.dto;

import com.marcos.estik.domain.enums.StorageEnum;

public record PcRequestDTO(
    String name,
    String processor,
    String memory,
    StorageEnum storageType,
    Integer storageCapacity
) {
    
}
