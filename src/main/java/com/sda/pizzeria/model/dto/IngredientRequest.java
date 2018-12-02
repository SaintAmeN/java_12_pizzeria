package com.sda.pizzeria.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientRequest {
    private String name;
    private boolean added;

    public IngredientRequest(String name, boolean added) {
        this.name = name;
        this.added = added;
    }
}
