package com.sda.pizzeria.controller;

import com.sda.pizzeria.model.AppUser;
import com.sda.pizzeria.model.UserCart;
import com.sda.pizzeria.service.AppUserAuthenticationService;
import com.sda.pizzeria.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(path = "/user/")
public class AppUserController {

    @Autowired
    public AppUserAuthenticationService appUserAuthenticationService;

    @Autowired
    public AppUserService appUserService;

    @GetMapping(path = "/profile")
    public String getProfile() {
        return "user/profile";
    }

    @GetMapping(path = "/cart")
    public String getCartView(Model model){
        Optional<AppUser> appUserOptional = appUserAuthenticationService.getLoggedInUser();
        if(appUserOptional.isPresent()){
            model.addAttribute("cart", appUserOptional.get().getUserCart());
            return "cart";
        }

        return "redirect:/login";
    }

    @PostMapping(path = "/updateCart")
    public String updateCart(Model model, UserCart userCart){
        Optional<UserCart> userCartOptional = appUserService.updateCart(userCart);
        if(userCartOptional.isPresent()){
            return "redirect:/user/cart";
        }

        Optional<AppUser> appUserOptional = appUserAuthenticationService.getLoggedInUser();
        model.addAttribute("message", "Unsuccessful cart update!");
        model.addAttribute("cart", appUserOptional.get().getUserCart());
        return "cart";
    }

    @GetMapping(path = "/removeFromCart/{id}")
    public String removeFromCart(Model model, @PathVariable(name = "id") Long orderId){
        Optional<UserCart> cartOptional = appUserService.removeFromCart(orderId);
        if(cartOptional.isPresent()){
            return "redirect:/user/cart";
        }

        Optional<AppUser> appUserOptional = appUserAuthenticationService.getLoggedInUser();
        model.addAttribute("message", "Unsuccessful cart update!");
        model.addAttribute("cart", appUserOptional.get().getUserCart());
        return "cart";
    }
}
