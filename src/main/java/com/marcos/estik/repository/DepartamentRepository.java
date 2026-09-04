package com.marcos.estik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.estik.domain.entity.Departament;

public interface DepartamentRepository extends JpaRepository<Departament, Long> {

    boolean existsByFacilityIdAndName(Long facilityId, String name);
    
}
