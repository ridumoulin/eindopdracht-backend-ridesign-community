package com.ridesigncommunity.RiDesignCommunity.service;

import com.ridesigncommunity.RiDesignCommunity.dto.ProductDto;
import com.ridesigncommunity.RiDesignCommunity.dto.ShoppingCartDto;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import com.ridesigncommunity.RiDesignCommunity.service.ProductService;
import com.ridesigncommunity.RiDesignCommunity.model.ShoppingCart;
import com.ridesigncommunity.RiDesignCommunity.model.User;
import com.ridesigncommunity.RiDesignCommunity.repository.ProductRepository;
import com.ridesigncommunity.RiDesignCommunity.repository.ShoppingCartRepository;
import com.ridesigncommunity.RiDesignCommunity.repository.UserRepository;
import com.ridesigncommunity.RiDesignCommunity.utils.ImageUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional
    public ShoppingCartDto getProductsInCartByEmail(String email) {
        Optional<ShoppingCart> cartOptional = Optional.ofNullable(shoppingCartRepository.findShoppingCartByUser_Email(email));
        if (cartOptional.isPresent()) {
            ShoppingCart cart = cartOptional.get();
            return mapToDto(cart);
        } else {
            throw new IllegalStateException("Shopping cart not found for user with email: " + email);
        }
    }

    @Transactional
    public ShoppingCartDto addProductToCart(Long productId, String email) {
        Optional<User> userOptional = userRepository.findById(email);
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

    @Transactional
    public void removeProductFromCart(String email, Long productId) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findByUser_Email(email);

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
        dto.setUserId(shoppingCart.getUser().getEmail());

        List<ProductDto> productDtos = shoppingCart.getProducts().stream()
                .map(product -> {
                    ProductDto productDto = new ProductDto();
                    productDto.setProductId(product.getProductId());
                    productDto.setCategory(product.getCategory());
                    productDto.setProductTitle(product.getProductTitle());
                    productDto.setImages(ImageUtil.decompressImageList(product.getImages()));
                    productDto.setPrice(product.getPrice());
                    productDto.setUsername(product.getUser().getUsername());
                    productDto.setDeliveryOptions(product.getDeliveryOptions());
                    return productDto;
                })
                .collect(Collectors.toList());

        dto.setProducts(productDtos);
        return dto;
    }
}
