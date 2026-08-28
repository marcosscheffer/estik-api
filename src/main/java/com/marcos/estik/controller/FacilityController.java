package com.marcos.estik.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.marcos.estik.domain.dto.FacilityRequestDTO;
import com.marcos.estik.domain.dto.FacilityResponseDTO;
import com.marcos.estik.service.FacilityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/facilities")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;

    @GetMapping("/{id}")
    public ResponseEntity<FacilityResponseDTO> getFacility(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(facilityService.getFacility(id));
    }

    @PostMapping
    public ResponseEntity<FacilityResponseDTO> createFacility(
        @RequestBody FacilityRequestDTO dto,
        UriComponentsBuilder uriBuilder
    ) {
        FacilityResponseDTO facility = facilityService.createFacility(dto);

        URI uri = uriBuilder
            .path("/facilities/{id}")    
            .buildAndExpand(facility.id())
            .toUri();

        return ResponseEntity.created(uri).body(facility);
    }

}
