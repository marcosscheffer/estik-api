package com.marcos.estik.service;

import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.facility.FacilitySummaryResponseDTO;
import com.marcos.estik.domain.dto.ticket.TicketRequestDTO;
import com.marcos.estik.domain.dto.ticket.TicketResponseDTO;
import com.marcos.estik.domain.dto.user.UserSummaryDTO;
import com.marcos.estik.domain.entity.Facility;
import com.marcos.estik.domain.entity.Ticket;
import com.marcos.estik.domain.entity.User;
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

    public TicketResponseDTO createTicket(TicketRequestDTO dto, User principal) {
        User  user = userService.getUserById(principal.getId());
        Facility facility = facilityService.getFacilityById(dto.facilityId());
        Ticket ticket = new Ticket(dto, user, facility);
        ticketRepository.save(ticket);
        return toDto(ticket);
    }
}
