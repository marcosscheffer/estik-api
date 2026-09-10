package com.marcos.estik.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.itemDepartament.ItemDepartamentRequestDTO;
import com.marcos.estik.domain.dto.itemDepartament.ItemDepartamentResponseDTO;
import com.marcos.estik.domain.entity.Departament;
import com.marcos.estik.domain.entity.ItemDepartament;
import com.marcos.estik.repository.ItemDepartamentRepository;

import jakarta.persistence.EntityNotFoundException;

import com.marcos.estik.domain.entity.Item;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemDepartamentService {
    private final ItemService itemService;
    private final DepartamentService departamentService;
    private final ItemDepartamentRepository itemDepartamentRepository;

    public ItemDepartamentResponseDTO toDto(ItemDepartament itemDepartament) {
        return new ItemDepartamentResponseDTO(
            itemDepartament.getId(),
            itemDepartament.getQuantity(),
            itemService.toDtoSummary(itemDepartament.getItem()),
            departamentService.toDtoSummary(itemDepartament.getDepartament())
        );
    }

    public ItemDepartament getItemDepartamentById(Long id) {
        return itemDepartamentRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Item not found")
            );
    }

    public ItemDepartamentResponseDTO createItemDepartament(ItemDepartamentRequestDTO dto) {
        Departament departament = departamentService.getDepartamentById(dto.departamentId());
        Item item = itemService.getItemById(dto.itemId());
        ItemDepartament itemDepartament = new ItemDepartament();

        
        itemDepartament.setQuantity(dto.quantity());
        itemDepartament.setItem(item);
        itemDepartament.setDepartament(departament);
        itemDepartamentRepository.save(itemDepartament);

        return toDto(itemDepartament);
        
    }

    public Page<ItemDepartamentResponseDTO> getAllItemDepartament(String q, Pageable pageable) {
        Page<ItemDepartament> itemsDepartaments = 
            itemDepartamentRepository.findByitemNameContainingIgnoreCase(q, pageable);
        return itemsDepartaments.map(item -> toDto(item));
    
    }

    public ItemDepartamentResponseDTO getItemDepartament(Long id) {
        ItemDepartament itemDepartament = getItemDepartamentById(id);
        return toDto(itemDepartament);
    }

    public void deleteItem(Long id) {
        itemDepartamentRepository.deleteById(id);
    }

    public ItemDepartamentResponseDTO updateItemDepartament(Long id, ItemDepartamentRequestDTO dto) {
        ItemDepartament itemDepartament = getItemDepartamentById(id);
        Departament departament = departamentService.getDepartamentById(dto.departamentId());
        Item item = itemService.getItemById(dto.itemId());

        itemDepartament.setDepartament(departament);
        itemDepartament.setItem(item);
        itemDepartament.setQuantity(dto.quantity());
        itemDepartamentRepository.save(itemDepartament);
        
        return toDto(itemDepartament);
    }

}
