package com.ridesigncommunity.RiDesignCommunity.service;

import com.ridesigncommunity.RiDesignCommunity.dto.ShoppingCartDto;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import com.ridesigncommunity.RiDesignCommunity.model.ShoppingCart;
import com.ridesigncommunity.RiDesignCommunity.model.User;
import com.ridesigncommunity.RiDesignCommunity.repository.ProductRepository;
import com.ridesigncommunity.RiDesignCommunity.repository.ShoppingCartRepository;
import com.ridesigncommunity.RiDesignCommunity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<Product> getProductsInCartByUserId(Long userId) {
        Optional<ShoppingCart> cartOptional = Optional.ofNullable(shoppingCartRepository.findByUserId(userId));
        if (cartOptional.isPresent()) {
            ShoppingCart cart = cartOptional.get();
            return cart.getProducts();
        } else {
            throw new IllegalStateException("Shopping cart not found for user with ID: " + userId);
        }
    }

    public ShoppingCartDto addProductToCart(Long productId, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (userOptional.isPresent() && productOptional.isPresent()) {
            User user = userOptional.get();
            Product product = productOptional.get();

            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            shoppingCart.getProducts().add(product);

            ShoppingCart savedCart = shoppingCartRepository.save(shoppingCart);
            return mapToDto(savedCart);
        } else {
            throw new IllegalArgumentException("User or Product not found");
        }
    }

    public void removeProductFromCart(Long userId, Long productId) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(userId);

        if (shoppingCartOptional.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOptional.get();

            Optional<Product> productToRemoveOptional = shoppingCart.getProducts().stream()
                    .filter(product -> product.getProductId().equals(productId))
                    .findFirst();

            if (productToRemoveOptional.isPresent()) {
                Product productToRemove = productToRemoveOptional.get();

                shoppingCart.getProducts().remove(productToRemove);

                shoppingCartRepository.save(shoppingCart);
            } else {
                throw new IllegalArgumentException("Product not found in the shopping cart");
            }
        } else {
            throw new IllegalStateException("Shopping cart not found for user");
        }
    }

    private ShoppingCartDto mapToDto(ShoppingCart shoppingCart) {
        ShoppingCartDto dto = new ShoppingCartDto();
        dto.setCartId(shoppingCart.getCartId());
        dto.setUserId(shoppingCart.getUser());
        dto.setProducts(shoppingCart.getProducts());
        return dto;
    }
}
