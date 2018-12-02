package com.sda.pizzeria.repository;

import com.sda.pizzeria.model.UserCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCartRepository extends JpaRepository<UserCart, Long> {
}
