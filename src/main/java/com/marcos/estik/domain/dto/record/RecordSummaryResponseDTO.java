package com.marcos.estik.domain.dto.record;

import java.time.LocalDateTime;

import com.marcos.estik.domain.dto.item.ItemSummaryResponseDTO;
import com.marcos.estik.domain.enums.RecordEnum;

public record RecordSummaryResponseDTO(
    Long id,
    LocalDateTime createdAt,
    RecordEnum direction,
    ItemSummaryResponseDTO item,
    Integer quantity
) {
    
}
