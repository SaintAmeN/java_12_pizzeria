package com.sda.pizzeria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user/")
public class AppUserController {

    @GetMapping(path = "/profile")
    public String getProfile() {
        return "user/profile";
    }
}
