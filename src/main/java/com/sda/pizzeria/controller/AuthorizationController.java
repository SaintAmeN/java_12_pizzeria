package com.sda.pizzeria.controller;

import com.sda.pizzeria.model.AppUser;
import com.sda.pizzeria.model.dto.request.RegisterUserRequest;
import com.sda.pizzeria.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
public class AuthorizationController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "authorization/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("formObject", new RegisterUserRequest());

        return "authorization/register";
    }

    @PostMapping("/register")
    public String sendRegister(Model model, RegisterUserRequest request) {
        // logika rejestracji

        Optional<AppUser> appUserOptional = appUserService.register(request);
        if (appUserOptional.isPresent()) {
            return "redirect:/login";
        }

        model.addAttribute("message", "Could not register!");
        model.addAttribute("formObject", request);

        return "authorization/register";
    }
}
