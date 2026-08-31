package com.marcos.estik.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.FacilitySummaryResponseDTO;
import com.marcos.estik.domain.dto.PcRequestDTO;
import com.marcos.estik.domain.dto.PcResponseDTO;
import com.marcos.estik.domain.dto.UserSummaryDTO;
import com.marcos.estik.domain.entity.Facility;
import com.marcos.estik.domain.entity.Pc;
import com.marcos.estik.domain.entity.User;
import com.marcos.estik.repository.FacilityRepository;
import com.marcos.estik.repository.PcRepository;
import com.marcos.estik.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PcService {
    private final PcRepository pcRepository;
    private final UserRepository userRepository;
    private final FacilityRepository facilityRepository;

    private PcResponseDTO toDTO(Pc pc) {
        return new PcResponseDTO (
                pc.getId(), 
                pc.getName(),
                new UserSummaryDTO(
                    pc.getAssembler().getId(), 
                    pc.getAssembler().getUsername()
                ),
                pc.getProcessor(),
                pc.getMemory(),
                pc.getStorageType(),
                pc.getStorageCapacity(),
                new FacilitySummaryResponseDTO(
                    pc.getFacility().getId(), 
                    pc.getFacility().getName(), 
                    pc.getFacility().getCode()
                )
            );
    }


    public Page<PcResponseDTO> getPcs(Pageable pageable) {
        Page<PcResponseDTO> pcs = pcRepository.findAll(pageable)
            .map(pc -> toDTO(pc));

        return pcs;
    }

     public Page<PcResponseDTO> getPcsByAssembler(Pageable pageable, Long assemblerId) {
        System.out.println(assemblerId);
        Page<PcResponseDTO> pcs = pcRepository.findByAssemblerId(assemblerId, pageable)
            .map(pc -> toDTO(pc));

        return pcs;
     }

     public Page<PcResponseDTO> getPcsByName(Pageable pageable, String q) {
        Page<PcResponseDTO> pcs = pcRepository.findByName(q, pageable)
            .map(pc -> toDTO(pc));

        return pcs;
     }

     public Page<PcResponseDTO> getPcsByNameAndAssembler(Pageable pageable, String q, Long assemblerId) {
        Page<PcResponseDTO> pcs = pcRepository.findByNameAndAssemblerId(q, assemblerId, pageable)
            .map(pc -> toDTO(pc));

        return pcs;
     }

    public PcResponseDTO getPc(Long id) {
        Pc pc = pcRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Pc not found"));

        return toDTO(pc);
    }

    public PcResponseDTO createPc(PcRequestDTO dto) {
        User assembler = userRepository.findById(dto.assemblerId()).orElseThrow(
            () -> new EntityNotFoundException("User not found")
        );

        Facility facility = facilityRepository.findById(dto.facilityId()).orElseThrow(
            () -> new EntityNotFoundException("Facility not found")
        );

        Pc pc = new Pc(dto);
        pc.setAssembler(assembler);
        pc.setFacility(facility);
        return toDTO(pc);
    }
}
