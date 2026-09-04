package com.marcos.estik.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.item.ItemRequestDTO;
import com.marcos.estik.domain.dto.item.ItemResponseDTO;
import com.marcos.estik.domain.dto.item.ItemSummaryResponseDTO;
import com.marcos.estik.domain.entity.Item;
import com.marcos.estik.repository.ItemRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private ItemResponseDTO toDto(Item item) {
        return new ItemResponseDTO(
                item.getId(), 
                item.getName(), 
                item.getDescription(),
                item.getCode(), 
                item.getQuantity()
            );
    }

    public ItemSummaryResponseDTO toDtoSummary(Item item) {
        return new ItemSummaryResponseDTO(
            item.getId(),
            item.getName(),
            item.getDescription(),
            item.getCode()
        );
    }

    public Page<ItemResponseDTO> getItems(Pageable pageable, String q) {
        return itemRepository.findByNameContainingIgnoreCase(q, pageable)
            .map(item -> toDto(item));
    }

    public ItemResponseDTO getItemById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Item not found with id: " + id)
        );
        return toDto(item);
    }


    public ItemResponseDTO createItem(ItemRequestDTO dto) {
        Item item = new Item(dto);

        itemRepository.save(item);

        return toDto(item);
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
        item.setQuantity(dto.quantity());
        itemRepository.save(item);
        return toDto(item);
    }
}
