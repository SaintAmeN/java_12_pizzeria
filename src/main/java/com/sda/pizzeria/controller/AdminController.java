package com.sda.pizzeria.controller;

import com.sda.pizzeria.model.Pizza;
import com.sda.pizzeria.model.dto.request.AddPizzaRequest;
import com.sda.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(path = "/admin/")
public class AdminController {
    @Autowired
    private PizzaService pizzaService;

    @GetMapping(path = "/pizzaForm")
    public String getPizzaForm(Model model) {
        model.addAttribute("formObject", new AddPizzaRequest());

        return "admin/pizzaForm";
    }

    @PostMapping(path = "/pizzaForm")
    public String sendNewPizza(Model model, AddPizzaRequest request) {
        Optional<Pizza> pizzaOptional = pizzaService.addPizza(request);
        if (pizzaOptional.isPresent()) {
            Pizza createdPizza = pizzaOptional.get();

            return "redirect:/admin/ingredients/" + createdPizza.getId();
        }

        model.addAttribute("message", "Could not add pizza!");
        model.addAttribute("formObject", request);

        return "admin/pizzaForm";
    }

    @GetMapping(path = "/ingredients/{id}")
    public String getIngredientsForm(Model model, @PathVariable(name = "id") Long id) {

        return "admin/ingredientsForm";
    }
}
