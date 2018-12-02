package com.sda.pizzeria.controller;

import com.sda.pizzeria.model.AppUser;
import com.sda.pizzeria.model.Pizza;
import com.sda.pizzeria.model.UserCart;
import com.sda.pizzeria.service.AppUserAuthenticationService;
import com.sda.pizzeria.service.AppUserService;
import com.sda.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppUserAuthenticationService appUserAuthenticationService;

    @ModelAttribute("loggedIn")
    public boolean getIsLoggedIn() {
        return appUserAuthenticationService.getLoggedInUser().isPresent();
    }

    @GetMapping(path = "/pizzas")
    public String getPizzas(Model model) {
        List<Pizza> pizzaList = pizzaService.getAllPizzas();

        model.addAttribute("pizzas", pizzaList);

        return "pizzas";
    }

    @GetMapping(path = "/addPizzaToCart/{pizzaId}")
    public String addToCart(Model model, @PathVariable(name = "pizzaId") Long id) {
        Optional<UserCart> cart = appUserService.addPizzaToCart(id);

        if (cart.isPresent()) {
            return "redirect:/pizzas";
        }
        model.addAttribute("message", "Could not add pizza");
        List<Pizza> pizzaList = pizzaService.getAllPizzas();
        model.addAttribute("pizzas", pizzaList);

        return "pizzas";
    }
}
