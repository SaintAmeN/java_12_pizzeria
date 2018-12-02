package com.sda.pizzeria.component;

import com.sda.pizzeria.model.AppUser;
import com.sda.pizzeria.model.UserCart;
import com.sda.pizzeria.model.UserRole;
import com.sda.pizzeria.model.dto.AddIngredientRequest;
import com.sda.pizzeria.repository.AppUserRepository;
import com.sda.pizzeria.repository.UserCartRepository;
import com.sda.pizzeria.repository.UserRoleRepository;
import com.sda.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${pizzeria.ingredients.default}")
    private String[] ingredients;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserCartRepository userCartRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // stworzenie użytkowników
        // stworzenie podstawowych uprawnień
        createInitialRoles();
        createInitialUsers();
        addDefaultIngredients();
    }

    private void addDefaultIngredients() {
        for (String ingredient : ingredients) {
            addIngredient(ingredient);
        }
    }

    private void addIngredient(String ingredient) {
        pizzaService.addIngredient(new AddIngredientRequest(ingredient));
    }

    private void createInitialUsers() {
        addUser("admin", "admin", "ROLE_USER", "ROLE_ADMIN");
        addUser("user", "user", "ROLE_USER");
    }

    private void addUser(String username, String password, String... roles) {
        Set<UserRole> userRoles = new HashSet<>();
        for (String role : roles) {
            Optional<UserRole> singleRole = userRoleRepository.findByName(role);
            if (singleRole.isPresent()) {
                userRoles.add(singleRole.get());
            }
        }
        // wszystkie role zebrane w secie.
        Optional<AppUser> searchedAppUser = appUserRepository.findByUsername(username);
        if (!searchedAppUser.isPresent()) {
            UserCart cart = new UserCart();
            cart = userCartRepository.save(cart);

            AppUser appUser = AppUser.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .userCart(cart)
                    .roles(userRoles).build();

            appUserRepository.save(appUser);
        }
    }

    private void createInitialRoles() {
        addRole("ROLE_USER");
        addRole("ROLE_ADMIN");
    }

    private void addRole(String name) {
        Optional<UserRole> searchedRole = userRoleRepository.findByName(name);
        if (!searchedRole.isPresent()) {
            UserRole role = new UserRole();
            role.setName(name);

            userRoleRepository.save(role);
        }
    }
}
