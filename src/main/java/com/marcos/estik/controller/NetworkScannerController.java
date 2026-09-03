package com.marcos.estik.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marcos.estik.domain.dto.common.IpResponseDTO;
import com.marcos.estik.service.NetworkScannerService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/ips")
@RequiredArgsConstructor
public class NetworkScannerController {

    private final NetworkScannerService networkScannerService;

    @GetMapping
    public ResponseEntity<List<IpResponseDTO>> scanNetwork(
        @RequestParam(defaultValue = "1") String subnet
    ) {
        
        return ResponseEntity.ok(networkScannerService.getIps(subnet));
    }
}