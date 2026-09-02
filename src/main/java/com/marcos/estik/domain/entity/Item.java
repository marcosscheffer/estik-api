package com.marcos.estik.domain.entity;

import com.marcos.estik.domain.dto.item.ItemRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @Column(nullable = false)

    private String name;
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer quantity;

    public Item(ItemRequestDTO dto) {
        this.name = dto.name();
        this.code = dto.code();
        this.description = dto.description();
        this.quantity = dto.quantity();
    }
}
