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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.marcos.estik.domain.dto.PcRequestDTO;
import com.marcos.estik.domain.dto.PcResponseDTO;
import com.marcos.estik.service.PcService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pcs")
@RequiredArgsConstructor
public class PcController {
    private final PcService pcService;

    @GetMapping
    public ResponseEntity<Page<PcResponseDTO>> getPcs(
        Pageable pageable,
        @RequestParam(defaultValue = "") String q,
        @RequestParam(defaultValue = "") String assembler
    ) {
        
        if (!assembler.isBlank()) {
            Long assemblerId = Long.valueOf(assembler);
            if (!q.isBlank()) {
                return ResponseEntity.ok(pcService.getPcsByNameAndAssembler(pageable, q, assemblerId));
            } else {
                return ResponseEntity.ok(pcService.getPcsByAssembler(pageable, assemblerId));
            }
        } else if (!q.isBlank()) {
            return ResponseEntity.ok(pcService.getPcsByName(pageable, q));
        } else {
            return ResponseEntity.ok(pcService.getPcs(pageable));
        }
    }    

    @GetMapping("/{id}")
    public ResponseEntity<PcResponseDTO> getPc(@PathVariable Long id) {
        return ResponseEntity.ok(pcService.getPc(id));
    }

    @PostMapping
    public ResponseEntity<PcResponseDTO> createPc(
        UriComponentsBuilder uriBuilder,
        @RequestBody PcRequestDTO dto
        ) {
        PcResponseDTO pc = pcService.createPc(dto);

        URI uri = uriBuilder
            .path("/pcs/{id}")
            .buildAndExpand(pc.id())
            .toUri();

        return ResponseEntity.created(uri).body(pc);
    }
}
