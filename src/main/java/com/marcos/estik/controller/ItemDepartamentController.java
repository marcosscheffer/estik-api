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

import com.marcos.estik.domain.dto.itemDepartament.ItemDepartamentRequestDTO;
import com.marcos.estik.domain.dto.itemDepartament.ItemDepartamentResponseDTO;
import com.marcos.estik.service.ItemDepartamentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/departaments/items")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ItemDepartamentController {
    private final ItemDepartamentService itemDepartamentService;

    @PostMapping
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<ItemDepartamentResponseDTO> createItemDepartament(
        @RequestBody @Valid ItemDepartamentRequestDTO dto,
        UriComponentsBuilder uriBuilder
    ) {
        ItemDepartamentResponseDTO itemDepartament = itemDepartamentService.createItemDepartament(dto);

        URI uri = uriBuilder
            .path("/departaments/items/{id}")
            .buildAndExpand(itemDepartament.id())
            .toUri();

        return ResponseEntity.created(uri).body(itemDepartament);
    }

    @GetMapping
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<Page<ItemDepartamentResponseDTO>> getAllItemDepartament(
        @RequestParam(defaultValue = "", name = "q") String q,
        Pageable pageable
    ) {
        return ResponseEntity.ok(itemDepartamentService.getAllItemDepartament(q, pageable));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<ItemDepartamentResponseDTO> getItemDepartament(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(itemDepartamentService.getItemDepartament(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<Void> deleteItemDepartament(
        @PathVariable Long id
    ) {
        itemDepartamentService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<ItemDepartamentResponseDTO> updateItemDepartament(
        @PathVariable Long id,
        @RequestBody @Valid ItemDepartamentRequestDTO dto
    ) {
        ItemDepartamentResponseDTO itemDepartament = 
            itemDepartamentService.updateItemDepartament(id, dto);
        return ResponseEntity.ok(itemDepartament);
    }
}
