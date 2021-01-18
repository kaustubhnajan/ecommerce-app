package com.ecommerce.repository;

import com.ecommerce.model.Product;

import java.util.List;

/**
 * Represents the custom repository for the product
 */
public interface ProductCustomRepository {

    /** Returns all the products matching with the given filter */
    List<Product> getProductByFilter(Product.ProductFilter ProductFilter);
}
