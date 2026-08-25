package com.marcos.estik.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.ItemRequestDTO;
import com.marcos.estik.domain.dto.ItemResponseDTO;
import com.marcos.estik.domain.entity.Item;
import com.marcos.estik.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Page<ItemResponseDTO> getItems(Pageable pageable, String q) {
        return itemRepository.findByNameContainingIgnoreCase(q, pageable)
            .map(item -> new ItemResponseDTO(
                item.getId(), 
                item.getName(), 
                item.getDescription(),
                item.getCode())
            );
    }

    public ItemResponseDTO createItem(ItemRequestDTO dto) {
        Item item = new Item(dto);

        itemRepository.save(item);

        return new ItemResponseDTO(
            item.getId(), 
            item.getName(), 
            item.getDescription(),
            item.getCode()
        );
    }
}
