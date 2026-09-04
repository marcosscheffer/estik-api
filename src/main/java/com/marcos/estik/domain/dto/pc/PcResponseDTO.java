package com.marcos.estik.domain.dto.pc;

import com.marcos.estik.domain.dto.departament.DepartamentSummaryDTO;
import com.marcos.estik.domain.dto.facility.FacilitySummaryResponseDTO;
import com.marcos.estik.domain.dto.user.UserSummaryDTO;
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
    FacilitySummaryResponseDTO facility,
    DepartamentSummaryDTO departament
) {
    
}
