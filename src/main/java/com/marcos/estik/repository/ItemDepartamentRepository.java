package com.marcos.estik.repository;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.estik.domain.entity.ItemDepartament;

public interface ItemDepartamentRepository extends JpaRepository<ItemDepartament, Long> {

    Page<ItemDepartament> findByitemNameContainingIgnoreCase(String q, Pageable pageable);
    
}
