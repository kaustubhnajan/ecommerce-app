package com.ecommerce.service;

import com.ecommerce.dto.ProductIdQuantityDTO;
import com.ecommerce.exception.InsufficientProductQuantityException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ShoppingCartRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Shopping cart service
 */
@Service
public class ShoppingCartService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ProductRepository productRepository;

    /** Returns shopping cart of specific user by user id */
    public User.ShoppingCart get(String userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));
        return user.getCart();
    }

    /** Add products to the shopping cart of specific user */
    public User.ShoppingCart addProducts(String userId, List<ProductIdQuantityDTO> productIdQuantityDTOs) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));

        User.ShoppingCart shoppingCart = user.getCart();
        List<User.CartProduct> existingProducts = shoppingCart.getProducts();

        List<User.CartProduct> productsToAdd = new ArrayList<>();
        Map<String, User.CartProduct> existingProductMap = new HashMap<>();

        existingProducts.parallelStream().forEach(existingProduct -> {
            if (!productIdQuantityDTOs.parallelStream()
                    .filter(productIdQuantityDTO -> productIdQuantityDTO.getProductId().equals(existingProduct.getProductId()))
                    .findFirst().isPresent()) {
                productsToAdd.add(existingProduct);
            }
            existingProductMap.put(existingProduct.getProductId(), existingProduct);
        });

        productIdQuantityDTOs.parallelStream().forEach(productIdQuantityDTO -> {
            Product product = productRepository.findById(productIdQuantityDTO.getProductId())
                                               .orElseThrow(() -> new ResourceNotFoundException(
                                                       "Product not found with given id: " + productIdQuantityDTO.getProductId()));

            if (product.getAvailableQuantity() < productIdQuantityDTO.getQuantity()) {
                throw new InsufficientProductQuantityException(
                        "Insufficient quantity for product with given id: " + productIdQuantityDTO.getProductId());
            }

            User.CartProduct cartProduct = existingProductMap.get(productIdQuantityDTO.getProductId());

            if (cartProduct != null) {
                cartProduct.setQuantity(cartProduct.getQuantity() + productIdQuantityDTO.getQuantity());

                if (product.getAvailableQuantity() < cartProduct.getQuantity()) {
                    throw new InsufficientProductQuantityException(
                            "Insufficient quantity for product with given id: " + productIdQuantityDTO.getProductId());
                }
            } else {
                cartProduct = new User.CartProduct(product.getId(), product.getName(), product.getType(),
                        product.getBrand(), product.getPrice(), productIdQuantityDTO.getQuantity(), shoppingCart);
            }
            productsToAdd.add(cartProduct);
        });

        double subTotal = 0;
        for (User.CartProduct product : productsToAdd) {
            subTotal += product.getQuantity() * product.getPrice();
        }

        shoppingCart.getProducts().clear();
        shoppingCart.getProducts().addAll(productsToAdd);
        shoppingCart.setSubTotal(subTotal);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }

    /** Remove products from the shopping cart of specific user */
    public User.ShoppingCart removeProducts(String userId, List<ProductIdQuantityDTO> productIdQuantityDTOs) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));

        User.ShoppingCart shoppingCart = user.getCart();
        List<User.CartProduct> existingProducts = shoppingCart.getProducts();

        List<User.CartProduct> productsToUpdate = new ArrayList<>();
        existingProducts.parallelStream().forEach(existingProduct -> {
            if (!productIdQuantityDTOs.parallelStream()
                    .filter(productIdQuantityDTO -> productIdQuantityDTO.getProductId().equals(existingProduct.getProductId()))
                    .findFirst().isPresent()) {
                productsToUpdate.add(existingProduct);
            }
        });

        productIdQuantityDTOs.parallelStream().forEach(productIdQuantityDTO -> {
            User.CartProduct cartProduct = existingProducts.parallelStream()
                                                   .filter(product -> product.getProductId().equals(productIdQuantityDTO.getProductId()))
                                                   .findFirst()
                                                   .orElseThrow(() -> new ResourceNotFoundException(
                                                           "Product not present in cart with given id: " + productIdQuantityDTO.getProductId()));

            if (productIdQuantityDTO.getQuantity() > cartProduct.getQuantity()) {
                throw new InsufficientProductQuantityException(
                        "Shopping cart dose not have sufficient quantity of given product with id: " + productIdQuantityDTO.getProductId());
            } else if (cartProduct.getQuantity() - productIdQuantityDTO.getQuantity() > 0) {
                cartProduct.setQuantity(cartProduct.getQuantity() - productIdQuantityDTO.getQuantity());
                productsToUpdate.add(cartProduct);
            } else {
                cartProduct.setCart(null);
            }
        });

        double subTotal = 0;
        for (User.CartProduct product : productsToUpdate) {
            subTotal += product.getQuantity() * product.getPrice();
        }

        shoppingCart.getProducts().clear();
        shoppingCart.getProducts().addAll(productsToUpdate);
        shoppingCart.setSubTotal(subTotal);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }
}
