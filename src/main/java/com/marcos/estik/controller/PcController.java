package com.marcos.estik.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.estik.service.PcService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pcs")
@RequiredArgsConstructor
public class PcController {
    private final PcService pcService;

    @GetMapping
    public ResponseEntity<Page<PcResponseDTO>> getPcs(Pageable pageable) {
        return 
    }    
}
