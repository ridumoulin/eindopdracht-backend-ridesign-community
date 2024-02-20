package com.ridesigncommunity.RiDesignCommunity.controller;

import com.ridesigncommunity.RiDesignCommunity.dto.ShoppingCartDto;
import com.ridesigncommunity.RiDesignCommunity.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/user/{userId}/products/{productId}/add-to-cart")
    public ResponseEntity<ShoppingCartDto> addProductToCart(@PathVariable Long userId, @PathVariable Long productId) {
        ShoppingCartDto updatedCart = shoppingCartService.addProductToCart(productId, userId);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ShoppingCartDto>> getUserShoppingCarts(@PathVariable Long userId) {
        List<ShoppingCartDto> userShoppingCarts = shoppingCartService.getUserShoppingCarts(userId);
        return new ResponseEntity<>(userShoppingCarts, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}/remove-from-cart/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        shoppingCartService.removeProductFromCart(userId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}