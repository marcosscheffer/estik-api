package com.marcos.estik.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.marcos.estik.domain.dto.ItemResponseDTO;
import com.marcos.estik.domain.dto.ItemRequestDTO;
import com.marcos.estik.service.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<Page<ItemResponseDTO>> getItems(
        @RequestParam(value = "q", defaultValue = "") String q,
        Pageable pageable
    ) {
        return ResponseEntity.ok(itemService.getItems(pageable, q));
    }

    @PostMapping
    public ResponseEntity<ItemResponseDTO> createItem(
        @RequestBody @Valid ItemRequestDTO dto,
        UriComponentsBuilder uriBuilder
    ) {
        ItemResponseDTO item = itemService.createItem(dto);

        URI uri = uriBuilder
          .path("/items/{id}")  
          .buildAndExpand(item.id())
          .toUri();

        return ResponseEntity.created(uri).body(item);
    }
}
