package com.marcos.estik.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.ItemRequestDTO;
import com.marcos.estik.domain.dto.ItemResponseDTO;
import com.marcos.estik.domain.entity.Item;
import com.marcos.estik.repository.ItemRepository;

import jakarta.persistence.EntityNotFoundException;
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
                item.getCode(),
                item.getQuantity()
            )
        );
    }

    public ItemResponseDTO getItemById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Item not found with id: " + id)
        );
        return new ItemResponseDTO(
            item.getId(),
            item.getName(),
            item.getDescription(),
            item.getCode(),
            item.getQuantity()
        );
    }


    public ItemResponseDTO createItem(ItemRequestDTO dto) {
        Item item = new Item(dto);

        itemRepository.save(item);

        return new ItemResponseDTO(
            item.getId(), 
            item.getName(), 
            item.getDescription(),
            item.getCode(),
            item.getQuantity()
        );
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public ItemResponseDTO updateItem(Long id, ItemRequestDTO dto) {
        Item item = itemRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Item not found with id: " + id)
        );
        item.setName(dto.name());
        item.setDescription(dto.description());
        item.setCode(dto.code());
        itemRepository.save(item);
        return new ItemResponseDTO(
            item.getId(), 
            item.getName(), 
            item.getDescription(),
            item.getCode(),
            item.getQuantity()
        );
    }
}
