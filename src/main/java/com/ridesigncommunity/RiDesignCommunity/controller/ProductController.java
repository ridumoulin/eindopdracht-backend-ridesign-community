package com.ridesigncommunity.RiDesignCommunity.controller;

import com.ridesigncommunity.RiDesignCommunity.dto.ProductDto;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import com.ridesigncommunity.RiDesignCommunity.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Transient;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + productDto.getProductId()).toUriString());
       return ResponseEntity.created(uri).body(createdProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(required = false) String category) {
        List<ProductDto> products;
        if (category != null) {
            products = productService.getProductsByCategory(category);
        } else {
            products = productService.getAllProducts();
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
        ProductDto product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            System.out.println("Product not found with ID: " + productId);
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty()) {
            List<ProductDto> products = productService.getProductsByCategory(category);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return ResponseEntity.ok().body(new ArrayList<>());
        }
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<List<Product>> getProductsByUserId(@PathVariable String username) {
        List<Product> products = productService.getProductsByUsername(username);
        return ResponseEntity.ok().body(products);
    }
}

