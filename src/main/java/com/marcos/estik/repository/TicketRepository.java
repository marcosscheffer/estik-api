package com.marcos.estik.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.estik.domain.entity.Ticket;
import com.marcos.estik.domain.enums.PriorityEnum;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Page<Ticket> findByUserId(Long id, Pageable pageable);

    Page<Ticket> findByPriorityAndTitleContainingIgnoreCase(PriorityEnum priority, String q, Pageable pageable);

    Page<Ticket> findByTitleContainingIgnoreCase(String q, Pageable pageable);
    
}
