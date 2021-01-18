package com.ecommerce.repository;

import com.ecommerce.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents the repository for the user
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
