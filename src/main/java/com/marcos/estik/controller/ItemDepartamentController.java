package com.marcos.estik.controller;

import java.net.URI;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.marcos.estik.domain.dto.itemDepartament.ItemDepartamentRequestDTO;
import com.marcos.estik.domain.dto.itemDepartament.ItemDepartamentResponseDTO;
import com.marcos.estik.domain.entity.ItemDepartament;
import com.marcos.estik.service.ItemDepartamentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/departaments/items")
@RequiredArgsConstructor
public class ItemDepartamentController {
    private final ItemDepartamentService itemDepartamentService;

    @PostMapping
    public ResponseEntity<ItemDepartamentResponseDTO> createItemDepartament(
        @RequestBody @Valid ItemDepartamentRequestDTO dto,
        UriComponentsBuilder uriBuilder
    ) {
        ItemDepartamentResponseDTO itemDepartament = itemDepartamentService.createItemDepartament(dto);

        URI uri = uriBuilder
            .path("/departaments/items/{id}")
            .buildAndExpand()
            .toUri();

        return ResponseEntity.created(uri).body(itemDepartament);
    }

    @GetMapping
    public ResponseEntity<Page<ItemDepartamentResponseDTO>> getAllItemDepartament(
        @RequestParam(defaultValue = "", name = "q") String q,
        Pageable pageable
    ) {
        return ResponseEntity.ok(itemDepartamentService.getAllItemDepartament(q, pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemDepartamentResponseDTO> getItemDepartament(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(itemDepartamentService.getItemDepartament(id));
    }
}
