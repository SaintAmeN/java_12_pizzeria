package com.sda.pizzeria.service;

import com.sda.pizzeria.model.Ingredient;
import com.sda.pizzeria.model.dto.AddIngredientRequest;
import com.sda.pizzeria.model.dto.IngredientRequest;
import com.sda.pizzeria.model.dto.IngredientsRequest;
import com.sda.pizzeria.model.dto.request.AddPizzaRequest;
import com.sda.pizzeria.model.Pizza;
import com.sda.pizzeria.repository.IngredientRepository;
import com.sda.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    public Optional<Pizza> addPizza(AddPizzaRequest addPizzaRequest) {
        Optional<Pizza> pizzaOptional = pizzaRepository.findByName(addPizzaRequest.getName());
        if (!pizzaOptional.isPresent()) {
            Pizza pizza = new Pizza();
            pizza.setName(addPizzaRequest.getName());
            pizza.setPrice(addPizzaRequest.getPrice());

            return Optional.of(pizzaRepository.save(pizza));
        }

        return Optional.empty();
    }

    public Optional<Ingredient> addIngredient(AddIngredientRequest addIngredientRequest) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findByName(addIngredientRequest.getName());
        if (!ingredientOptional.isPresent()) {
            return Optional.of(ingredientRepository.save(new Ingredient(null, addIngredientRequest.getName())));
        }
        return Optional.empty();
    }

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Optional<Pizza> updateIngredients(IngredientsRequest request) {
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(request.getPizzaId());
            if (!pizzaOptional.isPresent()) {
            return Optional.empty();
        }
        Pizza pizza = pizzaOptional.get();

        pizza.setProducts(findAllIngredients(request.getIngredients()
                .stream()
                .filter(IngredientRequest::isAdded)
                .map(IngredientRequest::getName)
                .collect(Collectors.toSet())));

        pizza = pizzaRepository.save(pizza);

        return Optional.of(pizza);
    }

    private Set<Ingredient> findAllIngredients(Set<String> ingredientNames) {
        return new HashSet<>(ingredientRepository.findAllByNameIn(ingredientNames));
    }
}
