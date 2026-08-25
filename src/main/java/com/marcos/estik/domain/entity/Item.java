package com.marcos.estik.domain.entity;

import java.util.List;

import com.marcos.estik.domain.dto.ItemRequestDTO;

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
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private String description;
    
    @OneToMany(
        targetEntity = Storage.class,
        mappedBy = "item"
    )
    private List<Storage> storage;

    public Item(ItemRequestDTO dto) {
        this.name = dto.name();
        this.code = dto.code();
        this.description = dto.description();
    }
}
