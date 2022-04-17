package com.petapp.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(precision = 10, scale = 2)
    @Type(type = "double")
    private double price;

    @Column(name = "available_amount")
    private int availableAmount;

}

