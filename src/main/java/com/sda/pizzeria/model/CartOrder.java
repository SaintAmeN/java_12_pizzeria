package com.sda.pizzeria.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Pizza pizza;

    private Integer quantity;
}
