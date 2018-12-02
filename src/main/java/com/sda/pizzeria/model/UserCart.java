package com.sda.pizzeria.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class UserCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    private List<CartOrder> orders;

}
