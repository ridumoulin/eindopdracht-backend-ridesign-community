package com.ridesigncommunity.RiDesignCommunity.service;

import com.ridesigncommunity.RiDesignCommunity.dto.ProductDto;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import com.ridesigncommunity.RiDesignCommunity.repository.ProductRepository;
import com.ridesigncommunity.RiDesignCommunity.repository.UserRepository;
import com.ridesigncommunity.RiDesignCommunity.utils.ImageUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    @Transactional
    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        List<ProductDto> availableProducts = new ArrayList<>();

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product p : allProducts) {
            productDtos.add(fromModelToProductDto(p));
        }

        for (ProductDto product : productDtos) {
            if (product.getPrice() > 0) {
                availableProducts.add(product);
            }
        }
        return availableProducts;
    }

    @Transactional
    public List<ProductDto> getProductsByCategory(String category) {
        String trimmedCategory = category.trim();
        List<Product> products = productRepository.findByCategoryIgnoreCase(trimmedCategory);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product p : products) {
            productDtos.add(fromModelToProductDto(p));
        }
        return productDtos;
    }

    public List<Product> getProductsByUsername(String username) {
        if (!userRepository.existsById(username)) {
            throw new EntityNotFoundException("User not found with ID: " + username);
        }
        return productRepository.findProductByUser_Username(username);
    }

    @Transactional
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.getReferenceById(productId);
        return fromModelToProductDto(product);
    }

    public List<ProductDto> searchProducts(String category, String productTitle) {
        List<Product> products = new ArrayList<>();
        List<ProductDto> productsDto = new ArrayList<>();
        if (category != null && productTitle != null) {
            products = productRepository.findByCategoryAndProductTitleContainingIgnoreCase(category, productTitle);
        } else if (category != null) {
            products = productRepository.findByCategoryIgnoreCase(category);
        } else if (productTitle != null) {
            products = productRepository.findByProductTitleContainingIgnoreCase(productTitle);
        } else {
            return getAllProducts();
        }
        if (!products.isEmpty()) {
            for (Product p : products) {
                productsDto.add(fromModelToProductDto(p));
            }
        }
        return productsDto;
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
        Product existingProduct = productRepository.getReferenceById(productId);
        if (existingProduct == null) {
            throw new EntityNotFoundException("Product not found with ID: " + productId);
        }
        productRepository.deleteById(productId);
    }

    private ProductDto fromModelToProductDto(Product product) {
        ProductDto pdto = new ProductDto();
        pdto.setProductId(product.getProductId());
        pdto.setCategory(product.getCategory());
        pdto.setProductTitle(product.getProductTitle());
        pdto.setDescription(product.getDescription());
        pdto.setImages(ImageUtil.decompressImageList(product.getImages()));
        pdto.setMaterials(product.getMaterials());
        pdto.setMeasurements(product.getMeasurements());
        pdto.setPrice(product.getPrice());
        pdto.setDeliveryOptions(product.getDeliveryOptions());
        pdto.setUsername(product.getUser().getUsername());
        return pdto;
    }

    public List<ProductDto> productDtoList(List<Product> productList) {
        List<ProductDto> productDtoList = new ArrayList<>();
        productList.forEach(product -> productDtoList.add(fromModelToProductDto(product)));
        return productDtoList;
    }
}



