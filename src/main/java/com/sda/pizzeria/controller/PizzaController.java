package com.sda.pizzeria.controller;

import com.sda.pizzeria.model.Pizza;
import com.sda.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping(path = "/pizzas")
    public String getPizzas(Model model) {
        List<Pizza> pizzaList = pizzaService.getAllPizzas();

        model.addAttribute("pizzas", pizzaList);

        return "pizzas";
    }
}
