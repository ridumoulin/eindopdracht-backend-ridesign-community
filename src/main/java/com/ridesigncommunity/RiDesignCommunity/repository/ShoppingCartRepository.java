package com.ridesigncommunity.RiDesignCommunity.repository;

import com.ridesigncommunity.RiDesignCommunity.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findShoppingCartByUser_Email(String email);

    Optional<ShoppingCart> findByUser_Email(String email);

}