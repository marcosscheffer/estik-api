package com.marcos.estik.domain.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "departaments",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_departament_name_id",
            columnNames = {"facility_id", "name"}
        )
    }
)
@Getter
@Setter
public class Departament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;
    
    @ManyToOne
    @JoinColumn(nullable = false, name = "facility_id")
    private Facility facility;

    @OneToMany(mappedBy = "departament")
    private List<Pc> pcs;

    @OneToMany(mappedBy = "departament")
    private List<ItemDepartament> items;

    @OneToMany(mappedBy = "departament")
    private List<Record> records;
}
