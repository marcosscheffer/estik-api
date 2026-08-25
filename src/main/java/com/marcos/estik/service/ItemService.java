package com.marcos.estik.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcos.estik.repository.ItemRepository;

import com.marcos.estik.domain.dto.ItemResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Page<ItemResponseDTO> getItems(Pageable pageable) {
        return itemRepository.findAll(pageable)
            .map(item -> new ItemResponseDTO(
                item.getId(), 
                item.getName(), 
                item.getDescription(),
                item.getCode())
            );
    }
}
