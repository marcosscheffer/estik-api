package com.marcos.estik.domain.dto;

import com.marcos.estik.domain.enums.OsEnum;
import com.marcos.estik.domain.enums.StorageEnum;

public record PcResponseDTO(
    Long id,
    String name,
    UserSummaryDTO assembler,
    String processor,
    String memory,
    StorageEnum StorageType,
    Integer storageCapacity,
    OsEnum os,
    FacilitySummaryResponseDTO facility
) {
    
}
