package com.ridesigncommunity.RiDesignCommunity.dto;

import com.ridesigncommunity.RiDesignCommunity.model.User;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ShoppingCartDto {

    public Long cartId;

    @NotNull
    public User userId;

    @NotNull
    @Size(max = 5)
    public Product productId;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }
}
