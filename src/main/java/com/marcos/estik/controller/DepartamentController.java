package com.marcos.estik.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.marcos.estik.domain.dto.departament.DepartamentRequestDTO;
import com.marcos.estik.domain.dto.departament.DepartamentResponseDTO;
import com.marcos.estik.domain.dto.departament.DepartamentSummaryDTO;
import com.marcos.estik.service.DepartamentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/departaments")
@RequiredArgsConstructor
public class DepartamentController {
    private final DepartamentService departamentService;

    @PostMapping
    public ResponseEntity<DepartamentResponseDTO> createDepartament(
        @RequestBody @Valid DepartamentRequestDTO dto,
        UriComponentsBuilder uriBuilder
    ) {
        DepartamentResponseDTO departament = departamentService.createDepartament(dto);
        URI uri = uriBuilder
            .path("/departaments/{id}")
            .buildAndExpand(departament.id())
            .toUri();

        return ResponseEntity.created(uri).body(departament);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentResponseDTO> getDepartament(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(departamentService.getDepartament(id));
    }

    @GetMapping
    public ResponseEntity<Page<DepartamentSummaryDTO>> getDepartaments(
        Pageable pageable
    ) {
        return ResponseEntity.ok(departamentService.getDepartaments(pageable));
    }
}
