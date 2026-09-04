package com.marcos.estik.service;

import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.item.ItemDepartamentResponseDTO;
import com.marcos.estik.domain.entity.ItemDepartament;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemDepartamentService {
    private final ItemService itemService;

    public ItemDepartamentResponseDTO toDto(ItemDepartament itemDepartament) {
        return new ItemDepartamentResponseDTO(
                    itemDepartament.getId(),
                    itemDepartament.getQuantity(),
                    itemService.toDtoSummary(itemDepartament.getItem())
                );
    }
}
