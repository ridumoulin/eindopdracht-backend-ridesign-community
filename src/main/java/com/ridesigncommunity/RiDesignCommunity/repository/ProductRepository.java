package com.ridesigncommunity.RiDesignCommunity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ridesigncommunity.RiDesignCommunity.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryAndProductTitleContainingIgnoreCase(String category, String productTitle);
    List<Product> findByCategoryIgnoreCase(String category);
    List<Product> findByProductTitleContainingIgnoreCase(String productTitle);
}
