package com.ridesigncommunity.RiDesignCommunity.service;

import com.ridesigncommunity.RiDesignCommunity.dto.ProductDto;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import com.ridesigncommunity.RiDesignCommunity.repository.ProductRepository;
import com.ridesigncommunity.RiDesignCommunity.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Product createProduct(ProductDto productDto) {
        validateProductDto(productDto);

        Product product = new Product();
        product.setProductTitle(productDto.getProductTitle());
        product.setCategory(productDto.getCategory());
        product.setMeasurements(productDto.getMeasurements());
        product.setMaterials(productDto.getMaterials());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDeliveryOptions(productDto.getDeliveryOptions());

        return productRepository.save(product);

    }


    public List<Product> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        List<Product> availableProducts = new ArrayList<>();

        for (Product product : allProducts) {
            if (product.getPrice() > 0) {
                availableProducts.add(product);
            }
        }
        return availableProducts;
    }

    public List<Product> getProductsByCategory(String category) {
        String trimmedCategory = category.trim().toLowerCase();
        return productRepository.findByCategory(trimmedCategory);
    }

    public List<Product> getProductsByUsername(String username) {
        if (!userRepository.existsById(username)) {
            throw new EntityNotFoundException("User not found with ID: " + username);
        }
        return productRepository.findProductByUser_Username(username);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
    }

    public List<Product> searchProducts(String category, String productTitle) {
        if (category != null && productTitle != null) {
            return productRepository. findByCategoryAndProductTitleContainingIgnoreCase(category, productTitle);
        } else if (category != null) {
            return productRepository.findByCategoryIgnoreCase(category);
        } else if (productTitle != null) {
            return productRepository.findByProductTitleContainingIgnoreCase(productTitle);
        } else {
            return getAllProducts();
        }
    }

    private void validateProductDto(ProductDto productDto) {
        if (productDto.getProductTitle() == null || productDto.getProductTitle().isEmpty()) {
            throw new IllegalArgumentException("Product title must not be empty");
        }
        if (productDto.getCategory() == null || productDto.getCategory().isEmpty()) {
            throw new IllegalArgumentException("Category must not be empty");
        }
        if (productDto.getMeasurements() == null || productDto.getMeasurements().isEmpty()) {
            throw new IllegalArgumentException("Measurements must not be empty");
        }
        if (productDto.getDescription() == null || productDto.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description must not be empty");
        }
        if (productDto.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }

    public void deleteProduct(Long productId) {
        Product existingProduct = getProductById(productId);
        if (existingProduct == null) {
            throw new EntityNotFoundException("Product not found with ID: " + productId);
        }
        productRepository.deleteById(productId);
    }
}

