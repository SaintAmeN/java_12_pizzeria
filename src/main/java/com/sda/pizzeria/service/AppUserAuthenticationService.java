package com.sda.pizzeria.service;

import com.sda.pizzeria.model.AppUser;
import com.sda.pizzeria.model.UserRole;
import com.sda.pizzeria.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppUserAuthenticationService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername(username);
        if (appUserOptional.isPresent()) {
            AppUser appUser = appUserOptional.get();

            List<String> roles = appUser.getRoles()
                    .stream()
                    .map(userRole -> userRole.getName().replace("ROLE_", ""))
                    .collect(Collectors.toList());

            return User.builder()
                    .username(appUser.getUsername())
                    .password(appUser.getPassword())
                    .roles(roles.toArray(new String[roles.size()]))
                    .build();
        }
        throw new UsernameNotFoundException("Username could not be found.");
    }
}
