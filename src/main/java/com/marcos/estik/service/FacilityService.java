package com.marcos.estik.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.facility.FacilityRequestDTO;
import com.marcos.estik.domain.dto.facility.FacilityResponseDTO;
import com.marcos.estik.domain.dto.pc.PcSummaryResponseDTO;
import com.marcos.estik.domain.dto.user.UserSummaryDTO;
import com.marcos.estik.domain.entity.Facility;
import com.marcos.estik.repository.FacilityRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;

    private FacilityResponseDTO toDto(Facility facility) {
        List<PcSummaryResponseDTO> pcs = (facility.getPcs() != null) ? 
            facility.getPcs().stream()
                .map(pc -> new PcSummaryResponseDTO(
                        pc.getId(),
                        pc.getName(),
                        new UserSummaryDTO(
                            pc.getAssembler().getId(), 
                            pc.getAssembler().getUsername(),
                            pc.getAssembler().getRole()
                        ),
                        pc.getProcessor(),
                        pc.getMemory(),
                        pc.getStorageType(),
                        pc.getStorageCapacity(),
                        pc.getOs()
                    )
                ).toList() :
                List.of();
        return new FacilityResponseDTO(
            facility.getId(),
            facility.getName(),
            facility.getCode(),
            pcs
        );
    }

    public Page<FacilityResponseDTO> getFacilities(String q, Pageable pageable) {
        return facilityRepository.findByNameContainingIgnoreCase(q, pageable)
            .map(facility -> toDto(facility));
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
}
