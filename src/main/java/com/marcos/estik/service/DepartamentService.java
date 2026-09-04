package com.marcos.estik.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.departament.DepartamentRequestDTO;
import com.marcos.estik.domain.dto.departament.DepartamentResponseDTO;
import com.marcos.estik.domain.dto.departament.DepartamentSummaryDTO;
import com.marcos.estik.domain.dto.item.ItemDepartamentResponseDTO;
import com.marcos.estik.domain.dto.pc.PcSummaryResponseDTO;
import com.marcos.estik.domain.dto.record.RecordSummaryResponseDTO;
import com.marcos.estik.domain.entity.Departament;
import com.marcos.estik.domain.entity.Facility;
import com.marcos.estik.repository.DepartamentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartamentService {
    private final DepartamentRepository departamentRepository;
    private final FacilityService facilityService;
    private final PcService pcService;
    private final ItemDepartamentService itemDepartamentService;
    private final RecordService recordService;

    public DepartamentResponseDTO toDto(Departament departament) {
        List<PcSummaryResponseDTO> pcs = departament.getPcs() != null
            ? departament.getPcs().stream()
                .map(pc -> pcService.toDtoSummary(pc)).toList()
                : List.of();
        
        List<ItemDepartamentResponseDTO> items = departament.getItems() != null
            ? departament.getItems().stream()
                .map(item -> itemDepartamentService.toDto(item)).toList()
                : List.of();
        
        List<RecordSummaryResponseDTO> records = departament.getRecords() != null
            ? departament.getRecords().stream()
                .map(recordItem -> recordService.toDto(recordItem)).toList()
                : List.of();

        return new DepartamentResponseDTO(
            departament.getId(),
            departament.getName(),
            facilityService.toDtoSummary(departament.getFacility()),
            pcs,
            items,
            records
        );
    }

    public DepartamentSummaryDTO toDtoSummary(Departament departament) {
        return new DepartamentSummaryDTO(
            departament.getId(),
            departament.getName(),
            facilityService.toDtoSummary(departament.getFacility())
        );
    }

    public Departament getDepartamentById(Long id) {
        return departamentRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Departament not found")
            );
    }

    public DepartamentResponseDTO createDepartament(DepartamentRequestDTO dto) {
        if (departamentRepository.existsByFacilityIdAndName(dto.facilityId(), dto.name())) {
            throw new DataIntegrityViolationException("An error occurred.");
        }

        Facility facility = facilityService.getFacilityById(dto.facilityId());
        Departament departament = new Departament();
        departament.setName(dto.name());
        departament.setFacility(facility);
        departamentRepository.save(departament);

        return toDto(departament);
    }

    public DepartamentResponseDTO getDepartament(Long id) {
        Departament departament = getDepartamentById(id);
        return toDto(departament);
    }

    public Page<DepartamentSummaryDTO> getDepartaments(Pageable pageable) {
        return departamentRepository.findAll(pageable)
            .map(departament -> toDtoSummary(departament));
    }

    public void deleteDepartament(Long id) {
        departamentRepository.deleteById(id);
    }

    public DepartamentResponseDTO updateDepartament(Long id, DepartamentRequestDTO dto) {
        Departament departament = getDepartamentById(id);
        Facility facility = facilityService.getFacilityById(dto.facilityId());
        departament.setName(dto.name());
        departament.setFacility(facility);
        departamentRepository.save(departament);
        return toDto(departament);
    }
}
