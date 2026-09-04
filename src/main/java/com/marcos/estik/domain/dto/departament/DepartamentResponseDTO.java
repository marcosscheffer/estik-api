package com.marcos.estik.domain.dto.departament;

import java.util.List;

import com.marcos.estik.domain.dto.facility.FacilitySummaryResponseDTO;
import com.marcos.estik.domain.dto.item.ItemDepartamentResponseDTO;
import com.marcos.estik.domain.dto.pc.PcSummaryResponseDTO;
import com.marcos.estik.domain.dto.record.RecordSummaryResponseDTO;

public record DepartamentResponseDTO(
    Long id,
    String name,
    FacilitySummaryResponseDTO facility,
    List<PcSummaryResponseDTO> pcs,
    List<ItemDepartamentResponseDTO> items,
    List<RecordSummaryResponseDTO> records
) {
    
}
