package com.marcos.estik.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.estik.domain.entity.Facility;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Page<Facility> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
