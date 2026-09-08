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

import com.marcos.estik.domain.dto.facility.FacilityRequestDTO;
import com.marcos.estik.domain.dto.facility.FacilityResponseDTO;
import com.marcos.estik.domain.dto.facility.FacilitySummaryResponseDTO;
import com.marcos.estik.service.FacilityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/facilities")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class FacilityController {
    private final FacilityService facilityService;

    @GetMapping
    public ResponseEntity<Page<FacilitySummaryResponseDTO>> getFacilities(
        Pageable pageable,
        @RequestParam(defaultValue = "") String q
    ) {
        return ResponseEntity.ok(facilityService.getFacilities(q, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacilityResponseDTO> getFacility(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(facilityService.getFacility(id));
    }

    @PostMapping
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<FacilityResponseDTO> createFacility(
        @RequestBody @Valid FacilityRequestDTO dto,
        UriComponentsBuilder uriBuilder
    ) {
        FacilityResponseDTO facility = facilityService.createFacility(dto);

        URI uri = uriBuilder
            .path("/facilities/{id}")    
            .buildAndExpand(facility.id())
            .toUri();

        return ResponseEntity.created(uri).body(facility);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<FacilityResponseDTO> updateFacility(
        @PathVariable Long id,
        @RequestBody FacilityRequestDTO dto
    ) {
        return ResponseEntity.ok(facilityService.updateFacility(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<Void> deleteFacility(
        @PathVariable Long id
    ) {
        facilityService.deleteFacility(id);
        return ResponseEntity.noContent().build();
    }

}
