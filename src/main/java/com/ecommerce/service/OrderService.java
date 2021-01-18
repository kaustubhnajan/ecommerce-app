package com.ecommerce.service;

import com.ecommerce.exception.InsufficientProductQuantityException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ShoppingCartRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Order Service
 */
@Service
public class OrderService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    /** Returns specific order by it's id associated with specific user */
    public Order get(String userId, String orderId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFoundException(
                                          "User not found with given id: " + userId));

        List<Order> orders = user.getOrders();
        Order placedOrder = orders.parallelStream()
                                  .filter(order -> order.getId().equals(orderId))
                                  .findFirst().orElseThrow(() -> new ResourceNotFoundException(
                                          "Order not found with given id: " + orderId));
        return placedOrder;
    }

    /** Returns all orders associated with specific user */
    public List<Order> getAll(String userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFoundException(
                                          "User not found with given id: " + userId));
        return user.getOrders();
    }

    /** Places an order for specific user */
    public Order placeOrder(String userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFoundException(
                                          "User not found with given id: " + userId));

        User.ShoppingCart shoppingCart = user.getCart();

        List<User.CartProduct> cartProducts = shoppingCart.getProducts();
        List<Product> products = new ArrayList<>();
        List<Order.OrderedProduct> orderedProducts = new ArrayList<>();

        Order order = new Order(user, shoppingCart.getSubTotal());
        cartProducts.parallelStream().forEach(cartProduct -> {
            Product product = productRepository.findById(cartProduct.getProductId())
                                               .orElseThrow(() -> new ResourceNotFoundException(
                                                       "Product not found with given id: " + cartProduct.getProductId()));

            if (product.getAvailableQuantity() < cartProduct.getQuantity()) {
                throw new InsufficientProductQuantityException(
                        "Insufficient quantity for product with given id: " + cartProduct.getProductId());
            }

            product.setAvailableQuantity(product.getAvailableQuantity() - cartProduct.getQuantity());
            products.add(product);
            orderedProducts.add(new Order.OrderedProduct(cartProduct.getProductId(), cartProduct.getName(),
                    cartProduct.getType(), cartProduct.getBrand(), cartProduct.getPrice(),
                    cartProduct.getQuantity(), order));
        });

        order.setProducts(orderedProducts);
        orderRepository.save(order);
        shoppingCart.getProducts().clear();
        shoppingCart.setSubTotal(0);
        shoppingCartRepository.save(shoppingCart);
        productRepository.saveAll(products);

        return order;
    }
}
