package com.marcos.estik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.estik.domain.entity.Facility;

public interface FacilityRepository extends JpaRepository<Facility, Long> {    
}
