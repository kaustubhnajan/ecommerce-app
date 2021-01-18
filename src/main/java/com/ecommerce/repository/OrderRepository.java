package com.ecommerce.repository;

import com.ecommerce.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents the repository for the order
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, String> {
}
