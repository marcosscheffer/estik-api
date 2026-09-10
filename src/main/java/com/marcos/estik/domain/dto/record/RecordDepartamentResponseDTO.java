package com.marcos.estik.domain.dto.record;

import java.time.LocalDateTime;

import com.marcos.estik.domain.dto.item.ItemSummaryResponseDTO;
import com.marcos.estik.domain.dto.pc.PcSummaryResponseDTO;
import com.marcos.estik.domain.enums.RecordEnum;

public record RecordDepartamentResponseDTO (
    Long id,
    LocalDateTime createdAt,
    RecordEnum direction,
    ItemSummaryResponseDTO item,
    Integer quantity,
    PcSummaryResponseDTO pc
) {

}
