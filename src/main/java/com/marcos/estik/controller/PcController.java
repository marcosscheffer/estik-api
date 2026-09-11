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

import com.marcos.estik.domain.dto.pc.PcRequestDTO;
import com.marcos.estik.domain.dto.pc.PcResponseDTO;
import com.marcos.estik.service.PcService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pcs")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PcController {
    private final PcService pcService;

    @GetMapping
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<Page<PcResponseDTO>> getPcs(
        Pageable pageable,
        @RequestParam(name = "q",defaultValue = "") String q,
        @RequestParam(name = "assembler",defaultValue = "") String assembler
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
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<PcResponseDTO> getPc(@PathVariable Long id) {
        return ResponseEntity.ok(pcService.getPc(id));
    }

    @PostMapping
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<PcResponseDTO> createPc(
        UriComponentsBuilder uriBuilder,
        @RequestBody @Valid PcRequestDTO dto
        ) {
        PcResponseDTO pc = pcService.createPc(dto);

        URI uri = uriBuilder
            .path("/pcs/{id}")
            .buildAndExpand(pc.id())
            .toUri();

        return ResponseEntity.created(uri).body(pc);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<PcResponseDTO> updatePc(
        @PathVariable Long id,
        @RequestBody @Valid PcRequestDTO dto
    ) {
        PcResponseDTO pc = pcService.updatePc(id, dto);
        return ResponseEntity.ok(pc);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<Void> deletePc(
        @PathVariable Long id
    ) {
        pcService.deletePc(id);
        return ResponseEntity.noContent().build();
    }
}
