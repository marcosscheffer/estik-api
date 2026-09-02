package com.marcos.estik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.estik.domain.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
}
