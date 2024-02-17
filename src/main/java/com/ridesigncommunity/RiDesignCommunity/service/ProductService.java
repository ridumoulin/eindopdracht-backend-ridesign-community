package com.ridesigncommunity.RiDesignCommunity.service;

import com.ridesigncommunity.RiDesignCommunity.dto.ProductDto;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import com.ridesigncommunity.RiDesignCommunity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductDto productDto) {
        validateProductDto(productDto);

        Product product = new Product();
        product.setProductTitle(productDto.getProductTitle());
        product.setCategory(productDto.getCategory());
        product.setMeasurements(productDto.getMeasurements());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDeliveryOptions(productDto.getDeliveryOptions());

        return productRepository.save(product);

    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.orElse(null);
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

