package com.ridesigncommunity.RiDesignCommunity.repository;

import com.ridesigncommunity.RiDesignCommunity.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
