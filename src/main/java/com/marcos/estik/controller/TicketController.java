package com.marcos.estik.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.marcos.estik.domain.dto.ticket.TicketRequestDTO;
import com.marcos.estik.domain.dto.ticket.TicketResponseDTO;
import com.marcos.estik.domain.entity.User;
import com.marcos.estik.service.TicketService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponseDTO> createTicket(
        @RequestBody @Valid TicketRequestDTO dto,
        @AuthenticationPrincipal User principal,
        UriComponentsBuilder uriBuilder
    ) {
        TicketResponseDTO ticket = ticketService.createTicket(dto, principal);
        URI uri = uriBuilder
            .path("/ticket/{id}")
            .buildAndExpand(ticket.id())
            .toUri();
        
        return ResponseEntity.created(uri).body(ticket);
    }
}
