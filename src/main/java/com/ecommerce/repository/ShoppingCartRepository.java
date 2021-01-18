package com.ecommerce.repository;

import com.ecommerce.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents the repository for the shopping cart
 */
@Repository
public interface ShoppingCartRepository extends CrudRepository<User.ShoppingCart, Integer> {
}
