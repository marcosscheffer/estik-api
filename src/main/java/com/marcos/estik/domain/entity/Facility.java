package com.marcos.estik.domain.entity;

import java.util.List;

import com.marcos.estik.domain.dto.facility.FacilityRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Facilities")
@Getter
@Setter
@NoArgsConstructor
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    
    @OneToMany
    private List<Pc> pcs;

    @OneToMany(mappedBy = "facility")
    private List<Ticket> tickets;

    public Facility(FacilityRequestDTO dto) {
        name = dto.name();
        code = dto.code();
    }
    
}
