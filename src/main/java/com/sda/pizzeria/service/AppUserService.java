package com.sda.pizzeria.service;

import com.sda.pizzeria.model.AppUser;
import com.sda.pizzeria.model.CartOrder;
import com.sda.pizzeria.model.Pizza;
import com.sda.pizzeria.model.UserCart;
import com.sda.pizzeria.model.dto.request.RegisterUserRequest;
import com.sda.pizzeria.repository.AppUserRepository;
import com.sda.pizzeria.repository.CartOrderRepository;
import com.sda.pizzeria.repository.UserCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService {

    @Autowired
    private AppUserAuthenticationService appUserAuthenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private UserCartRepository userCartRepository;

    @Autowired
    private CartOrderRepository cartOrderRepository;

    public Optional<AppUser> register(RegisterUserRequest registerUserRequest) {
        Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(registerUserRequest.getUsername());
        if (optionalAppUser.isPresent()) {
            // nie mogę zarejestrować
            return Optional.empty();
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(registerUserRequest.getUsername());
        appUser.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        appUser.setRoles(userRoleService.getDefaultUserRoles());

        //
        UserCart cart = new UserCart();
        cart = userCartRepository.save(cart);

        appUser.setUserCart(cart);

        return Optional.of(appUserRepository.save(appUser));
    }

    public Optional<UserCart> addPizzaToCart(Long pizzaId) {
        Optional<AppUser> loggedInUser = appUserAuthenticationService.getLoggedInUser();
        if (!loggedInUser.isPresent()) {
            return Optional.empty();
        }
        Optional<Pizza> optionalPizza = pizzaService.getPizzaWithId(pizzaId);
        if (!optionalPizza.isPresent()) {
            return Optional.empty();
        }

        AppUser appUser = loggedInUser.get();
        Pizza pizza = optionalPizza.get();

        UserCart cart = appUser.getUserCart();
        CartOrder order = new CartOrder();
        order.setPizza(pizza);
        order.setQuantity(1);

        order = cartOrderRepository.save(order);

        cart.getOrders().add(order);

        cart = userCartRepository.save(cart);

        return Optional.of(cart);
    }
}
