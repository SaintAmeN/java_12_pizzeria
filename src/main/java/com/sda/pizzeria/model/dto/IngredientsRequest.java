package com.sda.pizzeria.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientsRequest {
    private Long pizzaId;
    private List<IngredientRequest> ingredients;
}
