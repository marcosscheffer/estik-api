package com.marcos.estik.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.departament.DepartamentSummaryDTO;
import com.marcos.estik.domain.dto.facility.FacilityRequestDTO;
import com.marcos.estik.domain.dto.facility.FacilityResponseDTO;
import com.marcos.estik.domain.dto.facility.FacilitySummaryResponseDTO;
import com.marcos.estik.domain.entity.Facility;
import com.marcos.estik.repository.FacilityRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;

    public FacilityResponseDTO toDto(Facility facility) {
        List<DepartamentSummaryDTO> departaments = (facility.getDepartaments() != null) ? 
            facility.getDepartaments().stream()
                .map(departament -> new DepartamentSummaryDTO(
                    departament.getId(),
                    departament.getName()
                )).toList() 
                : List.of();
                
        return new FacilityResponseDTO(
            facility.getId(),
            facility.getName(),
            facility.getCode(),
            departaments
        );
    }

    public FacilitySummaryResponseDTO toDtoSummary(Facility facility) {
        return new FacilitySummaryResponseDTO(
            facility.getId(), 
            facility.getName(), 
            facility.getCode()
        );
    }

    public Page<FacilitySummaryResponseDTO> getFacilities(String q, Pageable pageable) {
        return facilityRepository.findByNameContainingIgnoreCase(q, pageable)
            .map(facility -> new FacilitySummaryResponseDTO(
                facility.getId(),
                facility.getName(),
                facility.getCode()
            ));
    }

    public FacilityResponseDTO createFacility(FacilityRequestDTO dto) {
        Facility facility = new Facility(dto);
        facilityRepository.save(facility);
        return toDto(facility);
    }

    public FacilityResponseDTO getFacility(Long id) {
        Facility facility = facilityRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Facility not found")
        );

        return toDto(facility);
    }

    public Facility getFacilityById(Long id) {
        return facilityRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Facility not found")
            );
    }

    public FacilityResponseDTO updateFacility(Long id, FacilityRequestDTO dto) {
        Facility facility = facilityRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Facility not found")
        );
        facility.setName(dto.name());
        facility.setCode(dto.code());
        facilityRepository.save(facility);
        return toDto(facility);
    }

    public void deleteFacility(Long id) {
        facilityRepository.deleteById(id);
    }
}
