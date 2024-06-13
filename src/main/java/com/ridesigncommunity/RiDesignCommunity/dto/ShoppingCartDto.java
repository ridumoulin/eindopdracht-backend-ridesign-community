package com.ridesigncommunity.RiDesignCommunity.dto;

import com.ridesigncommunity.RiDesignCommunity.model.User;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ShoppingCartDto {

    public Long cartId;

    @NotNull
    public String userId;

    @NotNull
    @Size(max = 5)
    public List<ProductDto> products;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public void setEmail(String email) {
    }
}
