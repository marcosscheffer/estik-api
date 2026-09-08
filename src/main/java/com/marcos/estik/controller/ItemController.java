package com.marcos.estik.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.marcos.estik.domain.dto.item.ItemRequestDTO;
import com.marcos.estik.domain.dto.item.ItemResponseDTO;
import com.marcos.estik.service.ItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<Page<ItemResponseDTO>> getItems(
        @RequestParam(value = "q", defaultValue = "") String q,
        Pageable pageable
    ) {
        return ResponseEntity.ok(itemService.getItems(pageable, q));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable("id") Long id) {
        ItemResponseDTO item = itemService.getItem(id);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
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


    @DeleteMapping("/{id}")
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<Void> deleteItem(@PathVariable("id") Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<ItemResponseDTO> updateItem(
        @PathVariable("id") Long id,
        @RequestBody @Valid ItemRequestDTO dto
    ) {
        ItemResponseDTO item = itemService.updateItem(id, dto);
        return ResponseEntity.ok(item);
    }

}
