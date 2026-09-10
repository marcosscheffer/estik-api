package com.marcos.estik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.estik.domain.entity.RecordDepartament;

public interface RecordDepartamentRepository extends JpaRepository<RecordDepartament, Long> {
    
}
