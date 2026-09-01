package com.marcos.estik.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.estik.domain.dto.HeartbeatRequestDTO;
import com.marcos.estik.service.HeartbeatService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/heartbeat")
@RequiredArgsConstructor
public class HeartbeatController {
    private final HeartbeatService heartbeatService;

    @PostMapping
    public ResponseEntity<Void> pingHeartbeat(
        @RequestBody @Valid HeartbeatRequestDTO dto
    ) {
        heartbeatService.pingHeartbeat(dto);
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<?> getHeartbeat() {
        return ResponseEntity.ok(heartbeatService.getHeartBeat());
    }
}
