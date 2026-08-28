package com.marcos.estik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.estik.domain.entity.Pc;

public interface PcRepository extends JpaRepository<Pc, Long> {
    
}
