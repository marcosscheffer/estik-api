package com.marcos.estik.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marcos.estik.repository.ItemRepository;

import com.marcos.estik.domain.dto.ItemResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemResponseDTO> getItems() {
        List<ItemResponseDTO> items = itemRepository.findAll()
            .stream()
            .map(item -> new ItemResponseDTO(item.getId(), item.getName(), 
                item.getDescription(), item.getCode()))
            .toList();
        return items;
    }
}
