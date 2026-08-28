package com.marcos.estik.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.estik.domain.entity.Pc;

public interface PcRepository extends JpaRepository<Pc, Long> {
    Page<Pc> findByAssemblerId(Long assemblerId, Pageable pageable);
    Page<Pc> findByName(String q, Pageable pageable);
    Page<Pc> findByNameAndAssemblerId(String q, Long assemblerId, Pageable pageable);
    
}
