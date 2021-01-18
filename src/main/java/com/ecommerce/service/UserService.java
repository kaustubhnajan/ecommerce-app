package com.ecommerce.service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User service
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /** Returns user with given user id */
    public User get(String userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));
        return user;
    }
}
