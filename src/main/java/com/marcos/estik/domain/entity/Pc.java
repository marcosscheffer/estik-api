package com.marcos.estik.domain.entity;

import com.marcos.estik.domain.enums.StorageEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pcs", schema = "api")
@Getter
@Setter
public class Pc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String processor;
    private String memory;

    @Enumerated(EnumType.STRING)
    private StorageEnum storageType;
    private Integer storageCapacity;
}
