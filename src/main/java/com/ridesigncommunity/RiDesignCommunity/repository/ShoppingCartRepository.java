package com.ridesigncommunity.RiDesignCommunity.repository;

import com.ridesigncommunity.RiDesignCommunity.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findShoppingCartByUser_Username(String username);
}
