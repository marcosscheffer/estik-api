package com.marcos.estik.service;

import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.record.RecordSummaryResponseDTO;
import com.marcos.estik.domain.entity.Record;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final ItemService itemService;

    public RecordSummaryResponseDTO toDto(Record recordItem) {
        return new RecordSummaryResponseDTO(
            recordItem.getId(),
            recordItem.getCreatedAt(),
            recordItem.getDirection(),
            itemService.toDtoSummary(recordItem.getItem()),
            recordItem.getQuantity()
        );
    }
}
