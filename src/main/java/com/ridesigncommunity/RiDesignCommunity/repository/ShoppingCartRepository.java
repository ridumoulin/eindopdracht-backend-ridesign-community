package com.ridesigncommunity.RiDesignCommunity.repository;

import com.ridesigncommunity.RiDesignCommunity.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findShoppingCartByUser_Username(String username);

    Optional<ShoppingCart> findByEmail(String email);

    ShoppingCart findShoppingCartByEmail(String email);
}
