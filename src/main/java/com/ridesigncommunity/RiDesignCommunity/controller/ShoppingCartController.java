package com.ridesigncommunity.RiDesignCommunity.controller;

import com.ridesigncommunity.RiDesignCommunity.dto.ProductDto;
import com.ridesigncommunity.RiDesignCommunity.dto.ShoppingCartDto;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import com.ridesigncommunity.RiDesignCommunity.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/user/{email}/products/{productId}/add-to-cart")
    public ResponseEntity<ShoppingCartDto> addProductToCart(@PathVariable String email, @PathVariable Long productId) {
        ShoppingCartDto updatedCart = shoppingCartService.addProductToCart(productId, email);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/user/{email}/products/{productId}/add-to-cart")
                .buildAndExpand(email, productId)
                .toUri();

        return ResponseEntity.created(uri).body(updatedCart);
    }

    @GetMapping("/user/{email}/products")
    public ResponseEntity<ShoppingCartDto> getProductsInCartByEmail(@PathVariable String email) {
        ShoppingCartDto cartDto = shoppingCartService.getProductsInCartByEmail(email);
        cartDto.setEmail(email);
        return ResponseEntity.ok().body(cartDto);
    }

    @DeleteMapping("/user/{email}/remove-from-cart/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable String email, @PathVariable Long productId) {
        shoppingCartService.removeProductFromCart(email, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}