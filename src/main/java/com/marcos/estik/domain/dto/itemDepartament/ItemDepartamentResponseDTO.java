package com.marcos.estik.domain.dto.itemDepartament;

import com.marcos.estik.domain.dto.departament.DepartamentSummaryDTO;
import com.marcos.estik.domain.dto.item.ItemSummaryResponseDTO;

public record ItemDepartamentResponseDTO(
    Long id,
    Integer quantity,
    ItemSummaryResponseDTO item,
    DepartamentSummaryDTO departament
) {
    
}
