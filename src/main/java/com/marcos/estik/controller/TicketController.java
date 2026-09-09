package com.marcos.estik.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import com.marcos.estik.domain.dto.ticket.TicketRequestDTO;
import com.marcos.estik.domain.dto.ticket.TicketResponseDTO;
import com.marcos.estik.domain.dto.ticket.TicketUpdateStatusDTO;
import com.marcos.estik.domain.entity.User;
import com.marcos.estik.domain.enums.PriorityEnum;
import com.marcos.estik.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
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

    @GetMapping
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<Page<TicketResponseDTO>> getTickets(
        @RequestParam(name = "q", defaultValue = "") String q,
        @RequestParam(name = "priority", required = false) PriorityEnum priority,
        Pageable pageable
    ) {
        return ResponseEntity.ok(ticketService.getTickets(priority, q, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> getTicket(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(ticketService.getTicket(id));
    }

    @GetMapping("/me")
    public ResponseEntity<Page<TicketResponseDTO>> getMyTickets(
        @AuthenticationPrincipal User principal,
        Pageable pageable
    ) {
        return ResponseEntity.ok(ticketService.getMyTickets(principal, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(
        @PathVariable Long id,
        @RequestBody @Valid TicketRequestDTO dto
    ) {
        return ResponseEntity.ok(ticketService.updateTicket(id, dto));
    }

    @PutMapping("/{id}/status")
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<TicketResponseDTO> updateTicketStatus(
        @PathVariable Long id,
        @RequestBody @Valid TicketUpdateStatusDTO dto
    ) {
        return ResponseEntity.ok(ticketService.updateTicketStatus(id, dto.status()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(
        @PathVariable Long id
    ) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
