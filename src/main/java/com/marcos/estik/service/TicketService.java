package com.marcos.estik.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.facility.FacilitySummaryResponseDTO;
import com.marcos.estik.domain.dto.ticket.TicketRequestDTO;
import com.marcos.estik.domain.dto.ticket.TicketResponseDTO;
import com.marcos.estik.domain.dto.user.UserSummaryDTO;
import com.marcos.estik.domain.entity.Facility;
import com.marcos.estik.domain.entity.Ticket;
import com.marcos.estik.domain.entity.User;
import com.marcos.estik.domain.enums.StatusEnum;
import com.marcos.estik.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final UserService userService;
    private final FacilityService facilityService;
    private final TicketRepository ticketRepository;

    private TicketResponseDTO toDto(Ticket ticket) {
        return new TicketResponseDTO(
            ticket.getId(),
            ticket.getStatus(),
            ticket.getTitle(),
            ticket.getDescription(),
            new UserSummaryDTO(
                ticket.getUser().getId(),
                ticket.getUser().getUsername(),
                ticket.getUser().getRole()
            ),
            new FacilitySummaryResponseDTO(
                ticket.getFacility().getId(),
                ticket.getFacility().getName(),
                ticket.getFacility().getCode()
            ),
            ticket.getCreatedAt(),
            ticket.getUpdatedAt()
        );
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Ticket not found")
        );
    }

    public TicketResponseDTO createTicket(TicketRequestDTO dto, User principal) {
        User  user = userService.getUserById(principal.getId());
        Facility facility = facilityService.getFacilityById(dto.facilityId());
        Ticket ticket = new Ticket(dto, user, facility);
        ticketRepository.save(ticket);
        return toDto(ticket);
    }

    public Page<TicketResponseDTO> getTickets(Pageable pageable) {
        return ticketRepository.findAll(pageable).map(ticket -> toDto(ticket));
    }

    public TicketResponseDTO getTicket(Long id) {
        return toDto(getTicketById(id));
    }

    public Page<TicketResponseDTO> getMyTickets(User principal, Pageable pageable) {
        return ticketRepository.findByUserId(principal.getId(), pageable).map(ticket -> toDto(ticket));
    }

    public TicketResponseDTO updateTicket(Long id, TicketRequestDTO dto) {
        Ticket ticket = getTicketById(id);
        Facility facility = facilityService.getFacilityById(dto.facilityId());
        ticket.setTitle(dto.title());
        ticket.setDescription(dto.description());
        ticket.setFacility(facility);
        ticket.setUpdatedAt(LocalDateTime.now());
        ticketRepository.save(ticket);
        return toDto(ticket);
    }

    public TicketResponseDTO updateTicketStatus(Long id, StatusEnum status) {
        Ticket ticket = getTicketById(id);
        ticket.setStatus(status);
        ticket.setUpdatedAt(LocalDateTime.now());
        ticketRepository.save(ticket);
        return toDto(ticket);
    }
}
