package com.sda.pizzeria.model.dto.request;

import lombok.Data;

@Data
public class AddPizzaRequest {
    private String name;
    private Double price;
}
