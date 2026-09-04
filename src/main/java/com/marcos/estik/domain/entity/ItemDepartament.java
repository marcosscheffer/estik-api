package com.marcos.estik.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "items_departaments",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_departament_item",
            columnNames = {"departament", "item"}
        )
    }
)
@Getter
@Setter
public class ItemDepartament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "departament", nullable = false)
    private Departament departament;

    @ManyToOne
    @JoinColumn(name = "item", nullable = false)
    private Item item;


    
}
