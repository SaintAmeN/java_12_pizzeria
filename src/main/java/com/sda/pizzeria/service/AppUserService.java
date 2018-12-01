package com.sda.pizzeria.service;

import com.sda.pizzeria.model.AppUser;
import com.sda.pizzeria.model.dto.request.RegisterUserRequest;
import com.sda.pizzeria.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserRoleService userRoleService;

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

        return Optional.of(appUserRepository.save(appUser));
    }
}
