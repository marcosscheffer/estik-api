package com.marcos.estik.domain.dto.pc;

import com.marcos.estik.domain.dto.common.UserSummaryDTO;
import com.marcos.estik.domain.enums.OsEnum;
import com.marcos.estik.domain.enums.StorageEnum;

public record PcSummaryResponseDTO(
    Long id,
    String name,
    UserSummaryDTO assembler,
    String processor,
    String memory,
    StorageEnum StorageType,
    Integer storageCapacity,
    OsEnum os
) {
    
}
