package com.marcos.estik.service;

import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.item.ItemSummaryResponseDTO;
import com.marcos.estik.domain.dto.pc.PcSummaryResponseDTO;
import com.marcos.estik.domain.dto.record.RecordDepartamentResponseDTO;
import com.marcos.estik.domain.entity.Departament;
import com.marcos.estik.domain.entity.Item;
import com.marcos.estik.domain.entity.Pc;
import com.marcos.estik.domain.entity.RecordDepartament;
import com.marcos.estik.domain.enums.RecordEnum;
import com.marcos.estik.repository.RecordDepartamentRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class RecordDepartamentService {
    private final RecordDepartamentRepository recordDepartamentRepository;
    private final UserService userService;

    public RecordDepartamentResponseDTO toDto(RecordDepartament recordDepartament) {
        return new RecordDepartamentResponseDTO(
            recordDepartament.getId(), 
            recordDepartament.getCreatedAt(), 
            recordDepartament.getDirection(), 
            (recordDepartament.getItem() == null) 
            ? null
            :  new ItemSummaryResponseDTO(
                recordDepartament.getItem().getId(),
                recordDepartament.getItem().getName(),
                recordDepartament.getItem().getDescription(),
                recordDepartament.getItem().getCode()
            ),
            recordDepartament.getQuantity(),
            (recordDepartament.getPc() == null)
            ? null
            : new PcSummaryResponseDTO(
                    recordDepartament.getPc().getId(),
                    recordDepartament.getPc().getName(),
                    userService.toDto(recordDepartament.getPc().getAssembler()),
                    recordDepartament.getPc().getProcessor(),
                    recordDepartament.getPc().getMemory(),
                    recordDepartament.getPc().getStorageType(),
                    recordDepartament.getPc().getStorageCapacity(),
                    recordDepartament.getPc().getOs()
            )
        );
    }

    public RecordDepartamentResponseDTO createRecordPc(Departament departament, Pc pc, RecordEnum direction) {
        RecordDepartament recordDepartament = new RecordDepartament(1, direction);
        recordDepartament.setDepartament(departament);
        recordDepartament.setPc(pc);
        recordDepartamentRepository.save(recordDepartament);

        return toDto(recordDepartament);
    }

    public RecordDepartamentResponseDTO createRecordItem(Departament departament, Item item, RecordEnum direction) {
        RecordDepartament recordDepartament = new RecordDepartament(1, direction);
        recordDepartament.setDepartament(departament);
        recordDepartament.setItem(item);
        recordDepartamentRepository.save(recordDepartament);

        return  toDto(recordDepartament);
    }      
}
