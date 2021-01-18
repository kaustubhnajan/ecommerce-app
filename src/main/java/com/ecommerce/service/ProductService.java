package com.ecommerce.service;

import com.ecommerce.exception.AccessRightException;
import com.ecommerce.exception.ResourceAlreadyExistException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Product Service
 */
@Service
public class ProductService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    /** Adds product to the system */
    public Product add(String userId, Product product) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));
        if (user.getType() != User.UserType.SELLER) {
            throw new AccessRightException("Only user of type seller can add the product to the system");
        }

        Optional<Product> existingProduct = productRepository.findById(product.getId());
        if (existingProduct.isPresent()) {
            throw new ResourceAlreadyExistException("Product already exists with given id: " + product.getId());
        }
        productRepository.save(product);
        return product;
    }

    /** Deletes product from the system */
    public void delete(String userId, String productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));
        if (user.getType() != User.UserType.SELLER) {
            throw new AccessRightException("Only user of type seller can delete the product to the system");
        }

        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new ResourceNotFoundException(
                                                   "Product not found with given id: " + productId));
        productRepository.delete(product);
    }

    /** Returns all the available products in the system */
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(product -> products.add(product));
        return products;
    }

    /** Returns all the products matching with the given filter */
    public List<Product> getByFilter(Product.ProductFilter productFilter) {
        Set<Product> products = new HashSet<>();
        if (!AppUtils.isEmpty(productFilter.getId())) {
            products.addAll(productRepository.findAll());
        }
        products.addAll(productRepository.getProductByFilter(productFilter));
        return new ArrayList<>(products);
    }

    /** Returns product by it's id */
    public Product getById(String productId) {
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new ResourceNotFoundException(
                                                   "Product not found with given id: " + productId));
        return product;
    }

    /** Updates the product details */
    public Product update(String userId, String productId, Product product) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));
        if (user.getType() != User.UserType.SELLER) {
            throw new AccessRightException("Only user of type seller can update the product to the system");
        }

        productRepository.findById(productId)
                         .orElseThrow(() -> new ResourceNotFoundException(
                                 "Product not found with given id: " + productId));
        productRepository.save(product);
        return product;
    }
}
