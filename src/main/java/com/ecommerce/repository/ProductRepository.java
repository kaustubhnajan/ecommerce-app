package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents the repository for the product
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String>,
        CrudRepository<Product, String>, ProductCustomRepository {
}
