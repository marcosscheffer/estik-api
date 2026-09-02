package com.marcos.estik.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.facility.FacilitySummaryResponseDTO;
import com.marcos.estik.domain.dto.pc.PcRequestDTO;
import com.marcos.estik.domain.dto.pc.PcResponseDTO;
import com.marcos.estik.domain.dto.user.UserSummaryDTO;
import com.marcos.estik.domain.entity.Facility;
import com.marcos.estik.domain.entity.Pc;
import com.marcos.estik.domain.entity.User;
import com.marcos.estik.repository.PcRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PcService {
    private final PcRepository pcRepository;
    private final FacilityService facilityService;
    private final UserService userService;

    private PcResponseDTO toDto(Pc pc) {
        return new PcResponseDTO (
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
                pc.getOs(),
                new FacilitySummaryResponseDTO(
                    pc.getFacility().getId(), 
                    pc.getFacility().getName(), 
                    pc.getFacility().getCode()
                )
            );
    }


    public Page<PcResponseDTO> getPcs(Pageable pageable) {
        Page<PcResponseDTO> pcs = pcRepository.findAll(pageable)
            .map(pc -> toDto(pc));

        return pcs;
    }

     public Page<PcResponseDTO> getPcsByAssembler(Pageable pageable, Long assemblerId) {
        System.out.println(assemblerId);
        Page<PcResponseDTO> pcs = pcRepository.findByAssemblerId(assemblerId, pageable)
            .map(pc -> toDto(pc));

        return pcs;
     }

     public Page<PcResponseDTO> getPcsByName(Pageable pageable, String q) {
        Page<PcResponseDTO> pcs = pcRepository.findByName(q, pageable)
            .map(pc -> toDto(pc));

        return pcs;
     }

     public Page<PcResponseDTO> getPcsByNameAndAssembler(Pageable pageable, String q, Long assemblerId) {
        Page<PcResponseDTO> pcs = pcRepository.findByNameAndAssemblerId(q, assemblerId, pageable)
            .map(pc -> toDto(pc));

        return pcs;
     }

    public PcResponseDTO getPc(Long id) {
        Pc pc = pcRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Pc not found"));

        return toDto(pc);
    }

    public PcResponseDTO createPc(PcRequestDTO dto) {
        User assembler = userService.getUserById(dto.assemblerId());

        Facility facility = facilityService.getFacilityById(dto.facilityId());

        Pc pc = new Pc(dto);
        pc.setAssembler(assembler);
        pc.setFacility(facility);
        pcRepository.save(pc);
        return toDto(pc);
    }


    public PcResponseDTO updatePc(Long id, PcRequestDTO dto) {
        Pc pc = pcRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("PC not found")
            );
        Facility facility = facilityService.getFacilityById(dto.facilityId());
        
        User assembler = userService.getUserById(dto.assemblerId());
        
        pc.setName(dto.name());
        pc.setFacility(facility);
        pc.setAssembler(assembler);
        pc.setProcessor(dto.processor());
        pc.setOs(dto.os());
        pc.setMemory(dto.memory());
        pc.setStorageType(dto.storageType());
        pc.setStorageCapacity(dto.storageCapacity());

        pcRepository.save(pc);
        return toDto(pc);
    }
}
