package com.sda.pizzeria.repository;

import com.sda.pizzeria.model.CartOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartOrderRepository extends JpaRepository<CartOrder, Long> {
}
