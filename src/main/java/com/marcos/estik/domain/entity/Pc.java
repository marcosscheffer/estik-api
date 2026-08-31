package com.marcos.estik.domain.entity;

import com.marcos.estik.domain.dto.pc.PcRequestDTO;
import com.marcos.estik.domain.enums.OsEnum;
import com.marcos.estik.domain.enums.StorageEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pcs")
@Getter
@Setter
@NoArgsConstructor
public class Pc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private User assembler;
    
    private String processor;
    private String memory;
    private Integer storageCapacity;

    @Enumerated(EnumType.STRING)
    private StorageEnum storageType;

    @Enumerated(EnumType.STRING)
    private OsEnum os;

    @ManyToOne
    private Facility facility;

    public Pc(PcRequestDTO dto) {
        name = dto.name();
        processor = dto.processor();
        memory = dto.memory();
        storageType = dto.storageType();
        storageCapacity = dto.storageCapacity();
        os = dto.os();
    }
    
}
